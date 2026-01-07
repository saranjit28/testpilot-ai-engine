package com.testpilot.ai;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class OllamaClient {

    private static final String OLLAMA_URL =
            "http://localhost:11434/api/generate";

    private static final HttpClient client = HttpClient.newHttpClient();

    public static String generate(String prompt) {

        String body = """
        {
          "model": "mistral",
          "prompt": "%s",
          "stream": false
        }
        """.formatted(prompt.replace("\"", "\\\""));

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return extractResponse(response.body());

        } catch (Exception e) {
            throw new RuntimeException("Ollama call failed", e);
        }
    }

    private static String extractResponse(String json) {
        int start = json.indexOf("\"response\":\"") + 12;
        int end = json.lastIndexOf("\"");
        return json.substring(start, end)
                .replace("\\n", "\n")
                .replace("\\\"", "\"");
    }
}