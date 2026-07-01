package SmartFlow.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/**
 * Scans every .java file inside stepDefinitions/ and extracts all
 * @Given / @When / @Then / @And / @But annotation patterns.
 *
 * Then, for each step in a generated feature file, reports whether a
 * matching step definition already exists or needs to be written.
 */
public class StepMatcher {

    private static final Logger log = LoggerFactory.getLogger(StepMatcher.class);

    private static final String STEP_DEFS_DIR = "src/test/java/stepDefinitions";

    // Matches:  @Given("some text")  @When("some {string} text")  etc.
    private static final Pattern ANNOTATION =
            Pattern.compile("@(?:Given|When|Then|And|But)\\(\"([^\"]+)\"\\)");

    // Matches a feature file step line
    private static final Pattern STEP_LINE =
            Pattern.compile("^\\s*(Given|When|Then|And|But)\\s+(.+)$");

    // ── Public API ─────────────────────────────────────────────────────────────

    public MatchResult match(String featureContent) {
        Set<String> existingPatterns = loadExistingPatterns();
        List<String> allSteps        = extractStepLines(featureContent);

        List<String> matched   = new ArrayList<>();
        List<String> unmatched = new ArrayList<>();

        for (String step : allSteps) {
            if (matchesAny(step, existingPatterns)) matched.add(step);
            else                                    unmatched.add(step);
        }

        log.info("[SmartFlow] Step match — reused: {}, new: {}", matched.size(), unmatched.size());
        return new MatchResult(matched, unmatched, existingPatterns.size());
    }

    // ── Internals ──────────────────────────────────────────────────────────────

    private Set<String> loadExistingPatterns() {
        Set<String> patterns = new LinkedHashSet<>();
        try {
            Files.walk(Paths.get(STEP_DEFS_DIR))
                 .filter(p -> p.toString().endsWith(".java"))
                 .forEach(p -> {
                     try {
                         Matcher m = ANNOTATION.matcher(Files.readString(p));
                         while (m.find()) patterns.add(m.group(1));
                     } catch (IOException e) {
                         log.warn("[SmartFlow] Cannot read step file: {}", p);
                     }
                 });
        } catch (IOException e) {
            log.warn("[SmartFlow] Cannot scan stepDefinitions dir: {}", e.getMessage());
        }
        log.debug("[SmartFlow] Loaded {} existing step patterns", patterns.size());
        return patterns;
    }

    private List<String> extractStepLines(String featureContent) {
        List<String> steps = new ArrayList<>();
        for (String line : featureContent.split("\n")) {
            Matcher m = STEP_LINE.matcher(line);
            if (m.matches()) steps.add(line.trim());
        }
        return steps;
    }

    /**
     * Checks whether a feature-file step matches any known annotation pattern.
     * Handles Cucumber expression parameters: {string}, {int}, {word}.
     */
    private boolean matchesAny(String stepLine, Set<String> patterns) {
        // Strip the keyword (Given/When/Then/And/But) to get just the step text
        String stepText = stepLine.replaceFirst("^(?:Given|When|Then|And|But)\\s+", "");

        for (String pattern : patterns) {
            // Exact match (case-insensitive)
            if (pattern.equalsIgnoreCase(stepText)) return true;

            // Convert Cucumber expression parameters to regex
            try {
                String regex = Pattern.quote(pattern)
                        .replace("\\{string\\}", "\"[^\"]*\"")
                        .replace("\\{int\\}",    "\\d+")
                        .replace("\\{word\\}",   "\\w+")
                        .replace("\\{float\\}",  "[\\d.]+");
                if (Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(stepText).matches())
                    return true;
            } catch (PatternSyntaxException ignored) {
                // fall through to next pattern
            }
        }
        return false;
    }

    // ── Result DTO ─────────────────────────────────────────────────────────────

    public static class MatchResult {
        public final List<String> matchedSteps;
        public final List<String> unmatchedSteps;
        public final int          totalExistingPatterns;

        public MatchResult(List<String> matched, List<String> unmatched, int total) {
            this.matchedSteps          = Collections.unmodifiableList(matched);
            this.unmatchedSteps        = Collections.unmodifiableList(unmatched);
            this.totalExistingPatterns = total;
        }

        public int    total()          { return matchedSteps.size() + unmatchedSteps.size(); }
        public double reusePercent()   {
            return total() == 0 ? 0.0 : matchedSteps.size() * 100.0 / total();
        }
    }
}
