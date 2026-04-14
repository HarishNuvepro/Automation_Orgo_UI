package AI_Framework;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReportManager {

    private static final String REPORT_PATH = "reports/healing_report.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private List<HealingSuggestion> suggestions;

    public ReportManager() {
        this.suggestions = new ArrayList<>();
        ensureReportDirectory();
    }

    private void ensureReportDirectory() {
        try {
            Files.createDirectories(Paths.get("reports"));
        } catch (IOException e) {
            System.err.println("[ReportManager] Failed to create reports directory: " + e.getMessage());
        }
    }

    public void logSuggestion(String element, String oldLocator, String suggestedLocator) {
        HealingSuggestion suggestion = new HealingSuggestion();
        suggestion.element = element;
        suggestion.old_locator = oldLocator;
        suggestion.suggested_locator = suggestedLocator;
        suggestion.status = "REVIEW_REQUIRED";
        suggestion.timestamp = System.currentTimeMillis();

        suggestions.add(suggestion);
        appendToReport(suggestion);

        System.out.println("[ReportManager] Logged suggestion for '" + element + "': " + suggestedLocator);
    }

    private void appendToReport(HealingSuggestion suggestion) {
        JsonArray array = new JsonArray();

        try {
            if (Files.exists(Paths.get(REPORT_PATH))) {
                try (FileReader reader = new FileReader(REPORT_PATH)) {
                    JsonArray existing = GSON.fromJson(reader, JsonArray.class);
                    if (existing != null) {
                        for (int i = 0; i < existing.size(); i++) {
                            array.add(existing.get(i));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[ReportManager] Reading existing report: " + e.getMessage());
        }

        JsonObject obj = new JsonObject();
        obj.addProperty("element", suggestion.element);
        obj.addProperty("old_locator", suggestion.old_locator);
        obj.addProperty("suggested_locator", suggestion.suggested_locator);
        obj.addProperty("status", suggestion.status);
        obj.addProperty("timestamp", suggestion.timestamp);

        array.add(obj);

        try (FileWriter writer = new FileWriter(REPORT_PATH)) {
            GSON.toJson(array, writer);
        } catch (IOException e) {
            System.err.println("[ReportManager] Failed to write report: " + e.getMessage());
        }
    }

    public void saveFullReport() {
        JsonArray array = new JsonArray();

        for (HealingSuggestion suggestion : suggestions) {
            JsonObject obj = new JsonObject();
            obj.addProperty("element", suggestion.element);
            obj.addProperty("old_locator", suggestion.old_locator);
            obj.addProperty("suggested_locator", suggestion.suggested_locator);
            obj.addProperty("status", suggestion.status);
            obj.addProperty("timestamp", suggestion.timestamp);
            array.add(obj);
        }

        try (FileWriter writer = new FileWriter(REPORT_PATH)) {
            GSON.toJson(array, writer);
        } catch (IOException e) {
            System.err.println("[ReportManager] Failed to save full report: " + e.getMessage());
        }
    }

    public List<HealingSuggestion> getSuggestions() {
        return new ArrayList<>(suggestions);
    }

    public void clear() {
        suggestions.clear();
    }

    public static class HealingSuggestion {
        public String element;
        public String old_locator;
        public String suggested_locator;
        public String status;
        public long timestamp;
    }
}