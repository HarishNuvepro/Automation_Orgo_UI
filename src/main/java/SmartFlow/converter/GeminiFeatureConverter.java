package SmartFlow.converter;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Sends raw browser-action JSON to the Gemini API and returns the
 * generated Cucumber .feature file text.
 *
 * Auto-discovers the best available model for the provided API key so the
 * code works with both old and new Google AI accounts without manual changes.
 */
public class GeminiFeatureConverter {

    private static final Logger log = LoggerFactory.getLogger(GeminiFeatureConverter.class);

    private static final String BASE    = "https://generativelanguage.googleapis.com";
    private static final String LIST_URL = BASE + "/v1beta/models?key=";
    private static final int    TIMEOUT_MS = 60_000;

    // Preferred models in priority order — first one available for this key wins
    private static final List<String> PREFERRED = Arrays.asList(
            "gemini-2.5-flash",
            "gemini-2.5-flash-lite",
            "gemini-flash-latest",
            "gemini-2.0-flash",
            "gemini-2.0-flash-001",
            "gemini-2.0-flash-lite-001",
            "gemini-2.0-flash-lite",
            "gemini-1.5-flash"
    );

    private final String        apiKey;
    private final PromptBuilder promptBuilder = new PromptBuilder();
    private       String        resolvedModel = null;

    public GeminiFeatureConverter(String apiKey) {
        if (apiKey == null || apiKey.isBlank())
            throw new IllegalArgumentException("Gemini API key must not be empty");
        this.apiKey = apiKey;
    }

    // ── Public API ─────────────────────────────────────────────────────────────

    public String convert(List<Map<String, Object>> rawActions, String moduleName) {
        String prompt = promptBuilder.build(rawActions, moduleName);
        log.info("[SmartFlow] Sending {} actions to Gemini for module '{}'", rawActions.size(), moduleName);

        try {
            if (resolvedModel == null) resolvedModel = resolveModel();
            log.info("[SmartFlow] Using model: {}", resolvedModel);
            String raw = callGemini(prompt, resolvedModel);
            return raw.replaceAll("(?s)```[a-zA-Z]*\\n?", "").replaceAll("```", "").trim();
        } catch (Exception e) {
            log.error("[SmartFlow] Gemini API call failed: {}", e.getMessage());
            throw new RuntimeException("SmartFlow — Gemini conversion failed", e);
        }
    }

    // ── Model discovery ────────────────────────────────────────────────────────

    private String resolveModel() throws Exception {
        Set<String> available = fetchAvailableModels();
        log.info("[SmartFlow] Available models for this key: {}", available);

        for (String preferred : PREFERRED) {
            if (available.contains(preferred)) {
                return preferred;
            }
        }

        // Fallback: use the first flash/lite model from the list
        for (String m : available) {
            if (m.contains("flash") || m.contains("lite")) return m;
        }

        // Last resort: use whatever model is available
        if (!available.isEmpty()) return available.iterator().next();

        throw new RuntimeException(
                "No usable Gemini model found for this API key. " +
                "Verify the key at https://aistudio.google.com");
    }

    private Set<String> fetchAvailableModels() throws Exception {
        URL url = URI.create(LIST_URL + apiKey).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(TIMEOUT_MS);
        conn.setReadTimeout(TIMEOUT_MS);

        int status = conn.getResponseCode();
        InputStream stream = (status == 200) ? conn.getInputStream() : conn.getErrorStream();
        String body = readAll(stream);

        if (status != 200) {
            throw new RuntimeException(
                    "Failed to list Gemini models (HTTP " + status + "). " +
                    "Check that your GEMINI_API_KEY is valid.\n" + body);
        }

        Set<String> names = new LinkedHashSet<>();
        JsonObject root = JsonParser.parseString(body).getAsJsonObject();
        if (!root.has("models")) return names;

        for (JsonElement el : root.getAsJsonArray("models")) {
            JsonObject model = el.getAsJsonObject();
            String fullName = model.get("name").getAsString(); // e.g. "models/gemini-2.0-flash-lite"
            String shortName = fullName.replace("models/", "");

            // Only include models that support generateContent
            if (model.has("supportedGenerationMethods")) {
                for (JsonElement m : model.getAsJsonArray("supportedGenerationMethods")) {
                    if ("generateContent".equals(m.getAsString())) {
                        names.add(shortName);
                        break;
                    }
                }
            }
        }
        return names;
    }

    // ── Gemini call ────────────────────────────────────────────────────────────

    private String callGemini(String prompt, String modelName) throws Exception {
        String endpoint = BASE + "/v1beta/models/" + modelName + ":generateContent?key=" + apiKey;
        URL url = URI.create(endpoint).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(TIMEOUT_MS);
        conn.setReadTimeout(TIMEOUT_MS);
        conn.setDoOutput(true);

        JsonObject requestBody = buildRequestBody(prompt);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(requestBody.toString().getBytes());
        }

        int status = conn.getResponseCode();
        InputStream stream = (status == 200) ? conn.getInputStream() : conn.getErrorStream();
        String responseText = readAll(stream);

        if (status != 200) {
            throw new RuntimeException("Gemini API returned HTTP " + status + ": " + responseText);
        }

        return extractText(responseText);
    }

    private JsonObject buildRequestBody(String prompt) {
        JsonObject part = new JsonObject();
        part.addProperty("text", prompt);

        JsonArray parts = new JsonArray();
        parts.add(part);

        JsonObject content = new JsonObject();
        content.add("parts", parts);

        JsonArray contents = new JsonArray();
        contents.add(content);

        JsonObject genConfig = new JsonObject();
        genConfig.addProperty("temperature", 0.2);
        genConfig.addProperty("maxOutputTokens", 2048);

        JsonObject body = new JsonObject();
        body.add("contents", contents);
        body.add("generationConfig", genConfig);
        return body;
    }

    private String extractText(String jsonResponse) {
        JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();
        return root.getAsJsonArray("candidates")
                   .get(0).getAsJsonObject()
                   .getAsJsonObject("content")
                   .getAsJsonArray("parts")
                   .get(0).getAsJsonObject()
                   .get("text").getAsString();
    }

    private String readAll(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append('\n');
        }
        return sb.toString();
    }
}
