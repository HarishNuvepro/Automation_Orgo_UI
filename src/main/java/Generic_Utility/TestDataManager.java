package Generic_Utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Singleton holder for all JSON test data.
 * Call initialize(eLib) once at suite startup (@Before with AtomicBoolean guard).
 * All subsequent reads are from in-memory maps — no file I/O per scenario.
 */
public class TestDataManager {

    private static final Logger       log         = LoggerFactory.getLogger(TestDataManager.class);
    private static final Gson         GSON        = new Gson();
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    // In-memory data — populated once, read-only after that
    private static Map<String, Map<String, String>> labData     = new HashMap<>();
    private static Map<String, String>               userData    = new HashMap<>();
    private static Map<String, String>               credentials = new HashMap<>();

    private TestDataManager() {}

    /**
     * Generates JSON from Excel (if not already done) then loads all JSON into memory.
     * Safe to call from parallel @Before hooks — only the first caller does work.
     */
    public static synchronized void initialize(ExcelUtility excel) {
        if (initialized.get()) return;

        try {
            DotEnvLoader.load();
            CredentialManager.setExcelFallback(excel);
            new ExcelToJsonConverter(excel).convertAll();
            loadAll();
            initialized.set(true);
            log.info("TestDataManager ready — {} lab test cases, {} user fields",
                    labData.size(), userData.size());
        } catch (Exception e) {
            log.error("TestDataManager initialization failed", e);
            throw new RuntimeException("Failed to initialize test data", e);
        }
    }

    // ── Public accessors ─────────────────────────────────────────────────────

    /**
     * Returns a mutable copy of the lab test data row for the given TC_ID.
     * Callers (ThreadLocal maps in step definitions) can modify the copy freely.
     */
    public static Map<String, String> getLabData(String tcId) {
        Map<String, String> data = labData.get(tcId);
        if (data == null) {
            throw new RuntimeException("Lab test data not found for TC_ID: " + tcId);
        }
        return new HashMap<>(data);
    }

    /** Returns an unmodifiable view of user test data. */
    public static Map<String, String> getUserData() {
        return Collections.unmodifiableMap(userData);
    }

    /** Returns a non-sensitive credential value (browser type, invalid test creds). */
    public static String getCredential(String key) {
        return credentials.getOrDefault(key, "");
    }

    // ── Internal ─────────────────────────────────────────────────────────────

    private static void loadAll() throws IOException {
        Type labType  = new TypeToken<Map<String, Map<String, String>>>(){}.getType();
        Type flatType = new TypeToken<Map<String, String>>(){}.getType();

        labData     = loadJson("lab_data.json",    labType);
        userData    = loadJson("user_data.json",   flatType);
        credentials = loadJson("credentials.json", flatType);
    }

    private static <T> T loadJson(String filename, Type type) throws IOException {
        Path path = Paths.get(ExcelToJsonConverter.OUTPUT_DIR, filename);
        try (Reader reader = Files.newBufferedReader(path)) {
            return GSON.fromJson(reader, type);
        }
    }
}
