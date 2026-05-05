package Generic_Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Persistent cross-run healing cache.
 *
 * Stores two maps in src/main/resources/healing_cache.json:
 *   healed  — originalLocator -> last successful healed locator + metadata
 *   failed  — originalLocator -> known Gemini failure entry
 *
 * On the next run:
 *   - A cached-healed locator is tried before the fallback library or Gemini.
 *   - A known-failure locator skips the Gemini API call entirely.
 *
 * Thread-safe: ConcurrentHashMap for reads; synchronized block for disk writes.
 */
public class HealingCache {

    private static final String CACHE_PATH = "src/main/resources/healing_cache.json";
    private static final Gson   GSON       = new GsonBuilder().setPrettyPrinting().create();

    private static final Logger log = LoggerFactory.getLogger(HealingCache.class);

    private static volatile HealingCache instance;
    private static final Object LOCK = new Object();

    private final ConcurrentHashMap<String, CacheEntry>   healed = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, FailureEntry> failed = new ConcurrentHashMap<>();

    private HealingCache() {
        load();
    }

    public static HealingCache getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new HealingCache();
                }
            }
        }
        return instance;
    }

    // ── public API ──────────────────────────────────────────────────────────

    /**
     * Returns the previously healed locator for this original, or null if not cached.
     */
    public String getHealedLocator(String originalLocator) {
        CacheEntry entry = healed.get(normalize(originalLocator));
        return entry != null ? entry.healedLocator : null;
    }

    /**
     * True when Gemini already failed for this locator on a previous run.
     * Prevents wasting quota on the same broken element repeatedly.
     */
    public boolean isKnownGeminiFailure(String originalLocator) {
        return failed.containsKey(normalize(originalLocator));
    }

    /**
     * Records a successful heal — called after fallback or AI healing works.
     * Removes the locator from the known-failure list if it was there.
     */
    public void recordSuccess(String originalLocator, String healedLocator,
                              String method, String elementName) {
        String key   = normalize(originalLocator);
        CacheEntry e = healed.computeIfAbsent(key, k -> new CacheEntry());
        e.healedLocator = healedLocator;
        e.healingMethod = method;
        e.elementName   = elementName;
        e.healCount++;
        e.lastHealedAt  = System.currentTimeMillis();
        failed.remove(key);
        save();
        log.info("CACHED {} heal: {} -> {}", method, shorten(originalLocator), healedLocator);
    }

    /**
     * Records that Gemini could not heal this locator.
     * Future runs will skip the Gemini call for it.
     */
    public void recordGeminiFailure(String originalLocator, String elementName) {
        String key    = normalize(originalLocator);
        FailureEntry e = failed.computeIfAbsent(key, k -> new FailureEntry());
        e.elementName  = elementName;
        e.failCount++;
        e.lastFailedAt = System.currentTimeMillis();
        save();
        log.info("Marked as known Gemini failure: {}", shorten(originalLocator));
    }

    /**
     * Removes a stale cache entry — called when a previously cached locator
     * stops working (page was refactored again).
     */
    public void invalidate(String originalLocator) {
        String key = normalize(originalLocator);
        if (healed.remove(key) != null) {
            save();
            log.info("Invalidated stale entry: {}", shorten(originalLocator));
        }
    }

    public int healedCount()  { return healed.size(); }
    public int failureCount() { return failed.size(); }

    // ── persistence ─────────────────────────────────────────────────────────

    private void load() {
        Path path = Paths.get(CACHE_PATH);
        if (!Files.exists(path)) {
            log.info("No cache file found — starting fresh.");
            return;
        }
        try (FileReader reader = new FileReader(path.toFile())) {
            Type type  = new TypeToken<CacheFile>() {}.getType();
            CacheFile f = GSON.fromJson(reader, type);
            if (f != null) {
                if (f.healed != null) healed.putAll(f.healed);
                if (f.failed != null) failed.putAll(f.failed);
            }
            log.info("Loaded — healed: {}, known failures: {}", healed.size(), failed.size());
        } catch (Exception e) {
            log.error("Load error", e);
        }
    }

    private synchronized void save() {
        try {
            Path path = Paths.get(CACHE_PATH);
            Files.createDirectories(path.getParent());
            CacheFile f = new CacheFile();
            f.healed = new HashMap<>(healed);
            f.failed = new HashMap<>(failed);
            try (FileWriter writer = new FileWriter(path.toFile())) {
                GSON.toJson(f, writer);
            }
        } catch (IOException e) {
            log.error("Save error", e);
        }
    }

    private static String normalize(String s)  { return s == null ? "" : s.trim(); }
    private static String shorten(String s)    { return s != null && s.length() > 60 ? s.substring(0, 60) + "..." : s; }

    // ── data model ──────────────────────────────────────────────────────────

    public static class CacheEntry {
        public String healedLocator;
        public String healingMethod;
        public String elementName;
        public int    healCount;
        public long   lastHealedAt;
    }

    public static class FailureEntry {
        public String elementName;
        public int    failCount;
        public long   lastFailedAt;
    }

    private static class CacheFile {
        Map<String, CacheEntry>   healed;
        Map<String, FailureEntry> failed;
    }
}
