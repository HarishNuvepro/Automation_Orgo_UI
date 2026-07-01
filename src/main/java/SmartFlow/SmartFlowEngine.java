package SmartFlow;

import Generic_Utility.CredentialManager;
import Generic_Utility.DotEnvLoader;
import SmartFlow.converter.GeminiFeatureConverter;
import SmartFlow.generator.FeatureFileWriter;
import SmartFlow.generator.StepMatcher;
import SmartFlow.recorder.ActionRecorder;
import SmartFlow.report.SmartFlowReport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * ┌─────────────────────────────────────────────────────────────────┐
 * │               SMART BROWSER FLOW — MAIN ENGINE                  │
 * ├─────────────────────────────────────────────────────────────────┤
 * │  Two modes:                                                     │
 * │                                                                 │
 * │  1. RECORD — open a live browser, capture user actions, then   │
 * │     convert them to a .feature file automatically.             │
 * │                                                                 │
 * │     mvn compile exec:java                                       │
 * │       -Dexec.mainClass="SmartFlow.SmartFlowEngine"             │
 * │       -Dexec.args="record roles_creation"                      │
 * │                                                                 │
 * │  2. CONVERT — re-process a previously saved JSON recording.    │
 * │                                                                 │
 * │     mvn compile exec:java                                       │
 * │       -Dexec.mainClass="SmartFlow.SmartFlowEngine"             │
 * │       -Dexec.args="convert smart-flow/recordings/X.json roles" │
 * └─────────────────────────────────────────────────────────────────┘
 *
 * All existing tests are completely unaffected by this class.
 */
public class SmartFlowEngine {

    // ── Entry point ────────────────────────────────────────────────────────────

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 1) { printUsage(); return; }

        // Load .env credentials (same as test runs)
        DotEnvLoader.load();

        switch (args[0].toLowerCase()) {
            case "record":
                String session = args.length > 1 ? args[1]
                        : "session_" + System.currentTimeMillis();
                recordAndGenerate(session);
                break;

            case "convert":
                if (args.length < 3) {
                    System.out.println("ERROR: convert needs <json-file> <module-name>");
                    printUsage();
                    return;
                }
                convertExisting(args[1], args[2]);
                break;

            default:
                System.out.println("Unknown command: " + args[0]);
                printUsage();
        }
    }

    // ── Mode 1: Record ─────────────────────────────────────────────────────────

    private static void recordAndGenerate(String sessionName) throws Exception {
        printBanner("RECORDER", sessionName);

        String url       = CredentialManager.getBaseUrl();
        String geminiKey = CredentialManager.getGeminiApiKey();

        try (Playwright pw = Playwright.create()) {

            Browser browser = pw.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setArgs(Collections.singletonList("--start-maximized")));

            BrowserContext ctx  = browser.newContext(
                    new Browser.NewContextOptions().setViewportSize(null));
            Page page = ctx.newPage();
            page.setDefaultTimeout(120_000);

            ActionRecorder recorder = new ActionRecorder(page);
            recorder.startRecording();

            // Navigate to the application
            page.navigate(url, new Page.NavigateOptions()
                    .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                    .setTimeout(120_000));

            System.out.println("  🟢  Browser is open and recording has started.");
            System.out.println("  👉  Perform your actions in the browser now.");
            System.out.println("  ⏹   When finished, come back here and press ENTER.\n");
            System.out.print("  > Press ENTER to stop recording: ");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

            List<Map<String, Object>> actions = recorder.stopRecording();
            System.out.println("\n  ✅  Recording stopped — " + actions.size() + " actions captured.");

            // Save raw JSON
            Path rawFile = recorder.saveToFile(actions, sessionName);
            System.out.println("  💾  Raw actions saved → " + rawFile);

            ctx.close();
            browser.close();

            // Ask for module name
            System.out.print("\n  Enter module name (e.g. roles, teams, approvals): ");
            String moduleName = new BufferedReader(new InputStreamReader(System.in))
                    .readLine().trim();
            if (moduleName.isEmpty()) moduleName = sessionName;

            processActions(actions, moduleName, geminiKey);
        }
    }

    // ── Mode 2: Convert existing JSON ──────────────────────────────────────────

    private static void convertExisting(String jsonPath, String moduleName) throws Exception {
        printBanner("CONVERTER", moduleName);

        Path file = Paths.get(jsonPath);
        if (!Files.exists(file)) {
            System.out.println("  ERROR: File not found → " + jsonPath);
            return;
        }

        List<Map<String, Object>> actions = new Gson().fromJson(
                Files.readString(file),
                new TypeToken<List<Map<String, Object>>>() {}.getType());

        System.out.println("  📂  Loaded " + actions.size() + " actions from: " + jsonPath);

        processActions(actions, moduleName, CredentialManager.getGeminiApiKey());
    }

    // ── Shared processing pipeline ─────────────────────────────────────────────

    private static void processActions(List<Map<String, Object>> actions,
                                       String moduleName,
                                       String geminiKey) throws Exception {

        System.out.println("\n  ⏳  Sending to Gemini AI — this may take a few seconds...");

        // Step 1 — Gemini converts raw actions → Cucumber feature text
        GeminiFeatureConverter converter = new GeminiFeatureConverter(geminiKey);
        String featureContent = converter.convert(actions, moduleName);

        System.out.println("\n  ──── GENERATED FEATURE FILE ───────────────────────────────");
        System.out.println(featureContent);
        System.out.println("  ────────────────────────────────────────────────────────────\n");

        // Step 2 — Write to FeatureFiles/generated/
        FeatureFileWriter writer  = new FeatureFileWriter();
        Path featureFile          = writer.write(featureContent, moduleName);

        // Step 3 — Match against existing step definitions
        StepMatcher matcher       = new StepMatcher();
        StepMatcher.MatchResult r = matcher.match(featureContent);

        // Step 4 — Print report
        new SmartFlowReport().print(moduleName, featureFile, r);
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private static void printBanner(String mode, String label) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║         SMART BROWSER FLOW — " + pad(mode, 23) + "║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  Session/Module : " + pad(label, 35) + "║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    private static void printUsage() {
        System.out.println("\n  SmartFlow Engine — Usage");
        System.out.println("  ─────────────────────────────────────────────────────────");
        System.out.println("  Record a new session:");
        System.out.println("    mvn compile exec:java \\");
        System.out.println("      -Dexec.mainClass=\"SmartFlow.SmartFlowEngine\" \\");
        System.out.println("      -Dexec.args=\"record <session-name>\"");
        System.out.println();
        System.out.println("  Re-convert an existing recording:");
        System.out.println("    mvn compile exec:java \\");
        System.out.println("      -Dexec.mainClass=\"SmartFlow.SmartFlowEngine\" \\");
        System.out.println("      -Dexec.args=\"convert smart-flow/recordings/<file>.json <module>\"");
        System.out.println();
    }

    private static String pad(String s, int width) {
        if (s.length() >= width) return s.substring(0, width);
        return String.format("%-" + width + "s", s);
    }
}
