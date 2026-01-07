package com.testpilot.ai.llm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class OllamaClient {

    private static final String OLLAMA_URL =
            "http://localhost:11434/api/generate";

    private static final String MODEL = "codellama";
    private static final int TIMEOUT_SECONDS = 10;

    public static String generate(String prompt) {

        // First attempt
        String response = call(prompt);

        if (isValid(response)) {
            return response;
        }

        // Retry once
        response = call(prompt);

        if (isValid(response)) {
            return response;
        }

        // Final fallback (SAFE)
        return "throw new RuntimeException(\"Implementation required\");";
    }

    private static String call(String prompt) {

        try {
            String body = """
                    {
                      "model": "%s",
                      "prompt": "%s",
                      "stream": false
                    }
                    """.formatted(MODEL, escape(prompt));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_URL))
                    .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return extractResponse(response.body());

        } catch (Exception e) {
            return "";
        }
    }

    private static boolean isValid(String response) {

        if (response == null || response.isBlank()) {
            return false;
        }

        // Must look like Java code
        return response.contains(";")
                || response.contains("throw")
                || response.contains("driver.");
    }

    private static String extractResponse(String json) {

        int idx = json.indexOf("\"response\":\"");
        if (idx == -1) return "";

        String raw = json.substring(idx + 12);

        raw = raw.substring(0, raw.indexOf("\"}"))
                .replace("\\n", "\n")
                .replace("\\\"", "\"");

        return raw.trim();
    }

    private static String escape(String input) {
        return input.replace("\"", "\\\"");
    }
}
