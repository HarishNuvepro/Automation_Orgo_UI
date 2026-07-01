package SmartFlow.converter;

import com.google.gson.GsonBuilder;

import java.util.*;

/**
 * Builds the Gemini prompt that converts raw browser-action JSON into a
 * Cucumber .feature file tailored to this framework's conventions.
 */
public class PromptBuilder {

    /**
     * Step patterns that already exist in this project's step-definition files.
     * Gemini is told to reuse these exact phrases wherever the recorded action matches.
     */
    private static final String EXISTING_STEPS =
            "  - Given open the browser and enter the Url\n" +
            "  - And login as tenant admin\n" +
            "  - And login as mspadmin\n" +
            "  - And login as sysadmin\n" +
            "  - And login as user\n" +
            "  - And click on logout\n" +
            "  - Then validate login page is displayed\n" +
            "  - When click on save button\n" +
            "  - When click on create button\n" +
            "  - When click on delete button to confirm\n" +
            "  - When navigate to organization and click on users tab\n" +
            "  - When navigate to organization and click on create new user tab\n" +
            "  - And click on create a user button\n" +
            "  - Then validate user created successfully\n" +
            "  - When click on {string} button\n" +
            "  - When navigate to {string} tab\n" +
            "  - And click on {string} link\n" +
            "  - Then validate {string} message is displayed\n";

    /**
     * @param actions    the raw action list from ActionRecorder
     * @param moduleName human name of the module being tested (e.g. "roles", "teams")
     * @return complete prompt string ready to send to Gemini
     */
    public String build(List<Map<String, Object>> actions, String moduleName) {
        String actionsJson = new GsonBuilder().setPrettyPrinting().create().toJson(actions);
        String moduleTitle = capitalize(moduleName);

        return  "You are a test automation expert specialising in Cucumber BDD.\n\n" +

                "TASK: Convert the raw browser-action JSON below into a valid Cucumber .feature file.\n\n" +

                "MODULE: " + moduleName + "\n\n" +

                "RAW BROWSER ACTIONS (JSON):\n" +
                actionsJson + "\n\n" +

                "REUSE THESE EXISTING STEP DEFINITIONS EXACTLY when the action matches:\n" +
                EXISTING_STEPS + "\n" +

                "OUTPUT RULES — follow all of these:\n" +
                "1.  Output ONLY the .feature file text. No markdown, no explanation.\n" +
                "2.  First line: Feature: " + moduleTitle + " Management\n" +
                "3.  Each scenario's first tag line: @" + moduleName.toLowerCase() + " @TC1 @regression\n" +
                "    (increment TC number for each additional scenario)\n" +
                "4.  Every scenario MUST start with:\n" +
                "      Given open the browser and enter the Url\n" +
                "      And login as tenant admin\n" +
                "5.  Every scenario MUST end with:\n" +
                "      And click on logout\n" +
                "      Then validate login page is displayed\n" +
                "6.  NEVER put CSS selectors, IDs, or XPaths inside step text.\n" +
                "7.  For input fields use the label name:  And enter role name as \"Support Agent\"\n" +
                "8.  For buttons use the button text:      When click on Create Role button\n" +
                "9.  For validations use visible text:     Then validate \"Role created successfully\" message is displayed\n" +
                "10. Merge consecutive related clicks into one logical step where sensible.\n" +
                "11. Replace actual test data values with readable quoted placeholders.\n" +
                "12. Use ONLY these Cucumber keywords: Feature, Scenario, Given, When, And, Then, But.\n";
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }
}
