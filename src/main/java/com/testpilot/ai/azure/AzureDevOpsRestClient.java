package com.testpilot.ai.azure;

import com.testpilot.ai.config.AzureDevOpsConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AzureDevOpsRestClient {

    private static final HttpClient client = HttpClient.newHttpClient();
        public static String getFileContent(String url) throws Exception {

            String authHeader = Base64.getEncoder()
                    .encodeToString((":" + AzureDevOpsConfig.PAT).getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Basic " + authHeader)
                    .header("Accept", "*/*")
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to fetch blob: " + response.statusCode());
            }

            return response.body();
        }

    public static String get(String url, String pat) throws Exception {

        URL apiUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
        conn.setRequestMethod("GET");

        String auth = ":" + pat;
        String encodedAuth = Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
        conn.setRequestProperty("Accept", "application/json");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        conn.getResponseCode() >= 400
                                ? conn.getErrorStream()
                                : conn.getInputStream(),
                        StandardCharsets.UTF_8
                )
        );

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        return response.toString();
    }
}

