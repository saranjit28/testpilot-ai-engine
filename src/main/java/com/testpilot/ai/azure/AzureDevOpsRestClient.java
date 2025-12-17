package com.testpilot.ai.azure;

import com.testpilot.ai.config.AzureDevOpsConfig;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.nio.charset.StandardCharsets;

public class AzureDevOpsRestClient {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static String getFileContent(String url) throws Exception {

        // Azure DevOps PAT auth (username empty, PAT as password)
        String authHeader = Base64.getEncoder()
                .encodeToString((":" + AzureDevOpsConfig.PAT).getBytes());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Basic " + authHeader)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        // IMPORTANT: Print body on failure
        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Azure DevOps API failed\n"
                            + "URL: " + url + "\n"
                            + "HTTP Status: " + response.statusCode() + "\n"
                            + "Response: " + response.body()
            );
        }

        return response.body();
    }

    /**
     * Generic GET call for Azure DevOps URLs (UI or API)
     */
    public static String get(String url) throws Exception {

        System.out.println("Calling URL:\n" + url);

        URL apiUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();

        conn.setRequestMethod("GET");
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(15000);

        String auth = ":" + AzureDevOpsConfig.PAT;
        String encodedAuth = Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
        conn.setRequestProperty("Accept", "*/*");

        int status = conn.getResponseCode();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        status >= 400
                                ? conn.getErrorStream()
                                : conn.getInputStream(),
                        StandardCharsets.UTF_8
                )
        );

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line).append("\n");
        }

        reader.close();

        if (status >= 400) {
            throw new RuntimeException(
                    "Azure DevOps GET failed\n"
                            + "HTTP Status: " + status + "\n"
                            + "Response:\n" + response
            );
        }

        return response.toString();
    }
}
