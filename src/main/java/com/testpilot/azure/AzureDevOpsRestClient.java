package com.testpilot.azure;

import com.testpilot.config.AzureDevOpsConfig;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AzureDevOpsRestClient {

    public static String get(String url) throws Exception {

        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        String auth = ":" + AzureDevOpsConfig.PAT;
        String encoded = Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Basic " + encoded);
        conn.setRequestProperty("Accept", "application/json");

        return new String(conn.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public static String getFileContent(String url) throws Exception {
        return get(url);
    }
}
