package SmartFlow.recorder;

import com.google.gson.GsonBuilder;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Records every user interaction (click / fill / navigate) in a live Playwright
 * browser session by injecting a JavaScript listener into each page.
 *
 * Usage:
 *   ActionRecorder recorder = new ActionRecorder(page);
 *   recorder.startRecording();
 *   // ... tester interacts with the browser ...
 *   List<Map<String, Object>> actions = recorder.stopRecording();
 */
public class ActionRecorder {

    private static final Logger log = LoggerFactory.getLogger(ActionRecorder.class);

    private final Page    page;
    private final String  initScript;        // loaded once from classpath resource
    private final List<Map<String, Object>> allActions = new ArrayList<>();
    private boolean recording = false;

    public ActionRecorder(Page page) {
        this.page       = page;
        this.initScript = loadScript();
    }

    // ── Public API ─────────────────────────────────────────────────────────────

    public void startRecording() {
        recording = true;

        // addInitScript runs the JS on EVERY new page load (including navigations)
        page.addInitScript(initScript);

        // For the page already open, inject immediately
        safeEval(initScript);

        // On each full navigation: flush pending JS actions → add navigate action → re-inject
        page.onFrameNavigated(frame -> {
            if (frame.parentFrame() != null || !recording) return;
            flushJsActions();
            allActions.add(buildNavigateEntry(page.url()));
            safeEval(initScript);  // re-establish listeners on the new page
        });

        log.info("[SmartFlow] Recording started. Interact with the browser, then call stopRecording().");
    }

    public List<Map<String, Object>> stopRecording() {
        recording = false;
        flushJsActions();
        log.info("[SmartFlow] Recording stopped — {} actions captured.", allActions.size());
        return Collections.unmodifiableList(allActions);
    }

    /**
     * Saves the raw action list as a pretty-printed JSON file under
     * smart-flow/recordings/<sessionName>.json
     */
    public Path saveToFile(List<Map<String, Object>> actions, String sessionName) throws IOException {
        Path dir  = Paths.get("smart-flow", "recordings");
        Files.createDirectories(dir);
        Path file = dir.resolve(sessionName + ".json");
        try (Writer w = Files.newBufferedWriter(file)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(actions, w);
        }
        log.info("[SmartFlow] Raw actions saved → {}", file.toAbsolutePath());
        return file;
    }

    // ── Internals ──────────────────────────────────────────────────────────────

    /** Pull accumulated JS actions into the Java list and clear the JS array. */
    @SuppressWarnings("unchecked")
    private void flushJsActions() {
        try {
            Object result = page.evaluate(
                    "typeof window.__sfActions !== 'undefined' ? window.__sfActions.splice(0) : []");
            if (result instanceof List<?>) {
                for (Object item : (List<?>) result) {
                    if (item instanceof Map) {
                        allActions.add((Map<String, Object>) item);
                    }
                }
            }
        } catch (Exception e) {
            // Page may be mid-navigation — ignore silently
        }
    }

    private void safeEval(String script) {
        try { page.evaluate(script); }
        catch (Exception ignored) { /* page not ready yet */ }
    }

    private Map<String, Object> buildNavigateEntry(String url) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("action",    "navigate");
        m.put("url",       url);
        m.put("timestamp", System.currentTimeMillis());
        return m;
    }

    /** Loads recorder_script.js from the classpath resource folder. */
    private static String loadScript() {
        try (InputStream is = ActionRecorder.class
                .getResourceAsStream("/SmartFlow/recorder_script.js")) {
            if (is == null) throw new IllegalStateException(
                    "recorder_script.js not found on classpath at /SmartFlow/recorder_script.js");
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load SmartFlow recorder script", e);
        }
    }
}
