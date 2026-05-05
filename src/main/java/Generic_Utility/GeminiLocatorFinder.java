package Generic_Utility;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeminiLocatorFinder {

    private static final Logger log = LoggerFactory.getLogger(GeminiLocatorFinder.class);
    
    private static final String GEMINI_BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-latest:generateContent";
    
    public static String findElementLocator(String pageHtml, String stepDescription, String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            log.error("API key not provided. Set GEMINI_API_KEY environment variable.");
            return null;
        }
        
        String prompt = buildPrompt(pageHtml, stepDescription);
        
        try {
            String response = sendRequest(prompt, apiKey);
            return parseLocatorFromResponse(response);
        } catch (Exception e) {
            log.error("Failed to find locator", e);
            return null;
        }
    }
    
    private static String buildPrompt(String pageHtml, String stepDescription) {
        String truncatedHtml = pageHtml.length() > 15000 ? pageHtml.substring(0, 15000) : pageHtml;
        
        return "You are an expert automation test engineer. Analyze the following HTML and find a reliable XPath or CSS selector for the element described in the step.\n\n" +
               "Step description: " + stepDescription + "\n\n" +
               "HTML snippet:\n" + truncatedHtml + "\n\n" +
               "Return ONLY the selector (XPath or CSS) that will reliably find this element. Do not include any explanation or markdown.\n" +
               "Examples of valid outputs:\n" +
               "- //button[@id='submit']\n" +
               "- .btn-primary\n" +
               "- //input[@placeholder='Search']\n\n" +
               "Selector:";
    }
    
    private static String sendRequest(String prompt, String apiKey) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        
        String requestBody = "{\n" +
            "  \"contents\": [{\n" +
            "    \"parts\": [{\n" +
            "      \"text\": \"" + prompt.replace("\"", "\\\"").replace("\n", "\\n") + "\"\n" +
            "    }]\n" +
            "  }],\n" +
            "  \"generationConfig\": {\n" +
            "    \"temperature\": 0.2,\n" +
            "    \"maxOutputTokens\": 200\n" +
            "  }\n" +
            "}";
        
        String url = GEMINI_BASE_URL + "?key=" + apiKey;
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .timeout(java.time.Duration.ofSeconds(30))
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("Gemini API error: " + response.statusCode() + " - " + response.body());
        }
        
        return response.body();
    }
    
    private static String parseLocatorFromResponse(String response) {
        try {
            String jsonStr = response;
            
            int textStart = jsonStr.indexOf("\"text\"");
            if (textStart == -1) {
                return null;
            }
            
            int colonIndex = jsonStr.indexOf(":", textStart);
            if (colonIndex == -1) {
                return null;
            }
            
            int valueStart = jsonStr.indexOf("\"", colonIndex + 1);
            if (valueStart == -1) {
                return null;
            }
            
            int valueEnd = jsonStr.indexOf("\"", valueStart + 1);
            if (valueEnd == -1) {
                return null;
            }
            
            String locator = jsonStr.substring(valueStart + 1, valueEnd).trim();
            
            locator = locator.replaceAll("^[`'\"]+|[`'\"]+$", "");
            
            if (isValidXPath(locator) || isValidCss(locator)) {
                return locator;
            }
            
            return null;
            
        } catch (Exception e) {
            log.error("Failed to parse response", e);
            return null;
        }
    }
    
    private static boolean isValidXPath(String selector) {
        return selector.startsWith("//") || selector.startsWith("/");
    }
    
    private static boolean isValidCss(String selector) {
        Pattern cssPattern = Pattern.compile("^[.#]?[a-zA-Z][a-zA-Z0-9_-]*|\\[.+]|:.+");
        return cssPattern.matcher(selector).matches();
    }
    
    public static void main(String[] args) {
        String testHtml = "<html><body><button id='submit-btn'>Submit</button></body></html>";
        String step = "Click submit button";
        
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey != null && !apiKey.isEmpty()) {
            String locator = findElementLocator(testHtml, step, apiKey);
            log.info("Generated locator: {}", locator);
        } else {
            log.info("Set GEMINI_API_KEY to test");
        }
    }
}