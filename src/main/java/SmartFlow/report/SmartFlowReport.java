package SmartFlow.report;

import SmartFlow.generator.StepMatcher.MatchResult;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Prints a formatted summary to the console and saves a JSON report under
 * smart-flow/reports/
 */
public class SmartFlowReport {

    private static final Logger log  = LoggerFactory.getLogger(SmartFlowReport.class);
    private static final int    WIDTH = 62;

    public void print(String moduleName, Path featureFile, MatchResult result) {

        String border = "╔" + "═".repeat(WIDTH) + "╗";
        String div    = "╠" + "═".repeat(WIDTH) + "╣";
        String foot   = "╚" + "═".repeat(WIDTH) + "╝";

        System.out.println("\n" + border);
        System.out.println(row("  SMART BROWSER FLOW — GENERATION REPORT"));
        System.out.println(div);
        System.out.println(row("  Module       : " + moduleName));
        System.out.println(row("  Feature File : " + featureFile.getFileName()));
        System.out.println(row("  Total Steps  : " + result.total()));
        System.out.println(row("  ✅ Reused    : " + result.matchedSteps.size()
                + "  (no new Java code needed)"));
        System.out.println(row("  ❌ New       : " + result.unmatchedSteps.size()
                + "  (need Java step definitions)"));
        System.out.println(row(String.format("  Reuse Rate   : %.0f%%", result.reusePercent())));
        System.out.println(div);

        if (!result.matchedSteps.isEmpty()) {
            System.out.println(row("  ✅ REUSED STEPS (Java code already exists):"));
            result.matchedSteps.forEach(s -> System.out.println(row("     • " + s)));
            System.out.println(div);
        }

        if (!result.unmatchedSteps.isEmpty()) {
            System.out.println(row("  ❌ NEW STEPS (write Java code for these):"));
            result.unmatchedSteps.forEach(s -> System.out.println(row("     • " + s)));
            System.out.println(div);
            System.out.println(row("  NEXT STEPS:"));
            System.out.println(row("  1. Create stepDefinitions/" + moduleName + ".java"));
            System.out.println(row("  2. Create POM/" + moduleName + "Page.java"));
            System.out.println(row("  3. Add lazy getter to Util/Pages.java"));
            System.out.println(row("  4. Run: mvn test -Dcucumber.filter.tags=\"@"
                    + moduleName.toLowerCase() + "\""));
        } else {
            System.out.println(row("  🎉 ALL STEPS EXIST — run the test immediately!"));
            System.out.println(row("  mvn test -Dcucumber.filter.tags=\"@"
                    + moduleName.toLowerCase() + "\""));
        }

        System.out.println(foot + "\n");

        saveJsonReport(moduleName, featureFile, result);
    }

    // ── Internals ──────────────────────────────────────────────────────────────

    private String row(String content) {
        // Pad or truncate content to fill the box exactly
        if (content.length() > WIDTH - 1) content = content.substring(0, WIDTH - 4) + "...";
        return "║ " + String.format("%-" + (WIDTH - 1) + "s", content) + "║";
    }

    private void saveJsonReport(String module, Path featureFile, MatchResult result) {
        try {
            Path dir = Paths.get("smart-flow", "reports");
            Files.createDirectories(dir);

            String ts   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path   file = dir.resolve(module.toLowerCase() + "_" + ts + "_report.json");

            JsonObject root = new JsonObject();
            root.addProperty("module",             module);
            root.addProperty("featureFile",        featureFile.toString());
            root.addProperty("generatedAt",        LocalDateTime.now().toString());
            root.addProperty("totalSteps",         result.total());
            root.addProperty("reusedSteps",        result.matchedSteps.size());
            root.addProperty("newSteps",           result.unmatchedSteps.size());
            root.addProperty("reuseRatePercent",   result.reusePercent());

            JsonArray reused = new JsonArray();
            result.matchedSteps.forEach(reused::add);
            root.add("reusedStepsList", reused);

            JsonArray newList = new JsonArray();
            result.unmatchedSteps.forEach(newList::add);
            root.add("newStepsList", newList);

            Files.writeString(file, new GsonBuilder().setPrettyPrinting().create().toJson(root));
            log.info("[SmartFlow] Report saved → {}", file.toAbsolutePath());

        } catch (IOException e) {
            log.warn("[SmartFlow] Could not save JSON report: {}", e.getMessage());
        }
    }
}
