package AI_Framework;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeminiClient {

    private static final Logger log = LoggerFactory.getLogger(GeminiClient.class);

    private static final String GEMINI_BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private static final int TIMEOUT_SECONDS = 30;
    private static final int MAX_TOKENS = 200;
    private static final double TEMPERATURE = 0.2;

    private String apiKey;

    public GeminiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public GeminiClient() {
        this.apiKey = System.getenv("GEMINI_API_KEY");
    }

    public String getSmartLocator(String html, String failedLocator, String elementName) {
        if (apiKey == null || apiKey.isEmpty()) {
            log.error("API key not configured. Set GEMINI_API_KEY environment variable.");
            return null;
        }

        if (html == null || html.isEmpty()) {
            log.error("HTML content is empty.");
            return null;
        }

        String prompt = buildPrompt(html, failedLocator, elementName);

        try {
            String response = sendRequest(prompt);
            String locator = parseLocator(response);

            if (locator != null && isValidLocator(locator)) {
                log.info("AI suggested locator for '{}': {}", elementName, locator);
                return locator;
            }
        } catch (Exception e) {
            log.error("Failed to get smart locator", e);
        }

        return null;
    }

    private String buildPrompt(String html, String failedLocator, String elementName) {
        String truncatedHtml = html.length() > 15000 ? html.substring(0, 15000) : html;
        String prevLocator = failedLocator != null ? failedLocator : "N/A";

        return "You are an expert QA automation engineer specializing in Playwright/Java locators.\n" +
            "Analyze the HTML below and generate a robust XPath or CSS selector for the element: " + elementName + "\n\n" +
            "Previously failed locator: " + prevLocator + "\n\n" +
            "HTML:\n" + truncatedHtml + "\n\n" +
            "Requirements:\n" +
            "- Return ONLY the selector (CSS or XPath)\n" +
            "- Prefer stable attributes (id, name, data-* , aria-*)\n" +
            "- Avoid fragile selectors (index, position)\n" +
            "- Use contains() for dynamic text\n" +
            "- Examples: #submit-btn, //button[@id='submit'], input[placeholder='Search']\n\n" +
            "Selector:";
    }

    private String sendRequest(String prompt) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String escapedPrompt = escapeJson(prompt);
        String requestBody = "{\n" +
            "  \"contents\": [{\n" +
            "    \"parts\": [{\n" +
            "      \"text\": \"" + escapedPrompt + "\"\n" +
            "    }]\n" +
            "  }],\n" +
            "  \"generationConfig\": {\n" +
            "    \"temperature\": " + String.format("%.1f", TEMPERATURE) + ",\n" +
            "    \"maxOutputTokens\": " + MAX_TOKENS + "\n" +
            "  }\n" +
            "}";

        String url = GEMINI_BASE_URL + "?key=" + apiKey;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("API error: " + response.statusCode() + " - " + response.body());
        }

        return response.body();
    }

    private String parseLocator(String response) {
        try {
            int textStart = response.indexOf("\"text\"");
            if (textStart == -1) return null;

            int colonIndex = response.indexOf(":", textStart);
            if (colonIndex == -1) return null;

            int valueStart = response.indexOf("\"", colonIndex + 1);
            if (valueStart == -1) return null;

            int valueEnd = response.indexOf("\"", valueStart + 1);
            if (valueEnd == -1) return null;

            String locator = response.substring(valueStart + 1, valueEnd).trim();
            locator = locator.replaceAll("^[`'\"]+|[`'\"]+$", "");

            return locator;
        } catch (Exception e) {
            log.error("Parse error", e);
            return null;
        }
    }

    private boolean isValidLocator(String locator) {
        if (locator == null || locator.isEmpty()) return false;

        locator = locator.trim();

        if (locator.startsWith("//") || locator.startsWith("/")) {
            return true;
        }

        return locator.matches("^[.#]?[a-zA-Z][a-zA-Z0-9_-]*|\\[.+]");
    }

    private String escapeJson(String text) {
        return text
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}