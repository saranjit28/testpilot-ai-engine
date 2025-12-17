package com.testpilot.ai.runner;

import com.testpilot.ai.azure.AzureDevOpsRestClient;
import com.testpilot.ai.config.AzureDevOpsConfig;
import org.json.JSONArray;
import org.json.JSONObject;

public class StepsFileLister {

    public static void main(String[] args) throws Exception {

        // ✅ LIST FILES
        String listUrl =
                AzureDevOpsConfig.BASE_URL + "/"
                        + AzureDevOpsConfig.COLLECTION + "/"
                        + AzureDevOpsConfig.PROJECT
                        + "/_apis/git/repositories/"
                        + AzureDevOpsConfig.REPO_ID
                        + "/items"
                        + "?scopePath=/src/test/java/com/automation/ospi/steps"
                        + "&recursionLevel=Full"
                        + "&includeContentMetadata=true"
                        + "&api-version=3.0";

        System.out.println("LIST URL:");
        System.out.println(listUrl);

        String response = AzureDevOpsRestClient.get(listUrl);

        JSONObject json = new JSONObject(response);
        JSONArray items = json.getJSONArray("value");

        for (int i = 0; i < items.length(); i++) {

            JSONObject item = items.getJSONObject(i);

            if (item.optBoolean("isFolder")) continue;

            String path = item.getString("path");

            if (path.endsWith("Steps.java")) {
                System.out.println("\nFOUND: " + path);
                fetchAndPrintFile(path);
            }
        }
    }

    private static void fetchAndPrintFile(String filePath) throws Exception {

        String encodedPath = java.net.URLEncoder.encode(
                filePath,
                java.nio.charset.StandardCharsets.UTF_8
        );

        String fileUrl =
                AzureDevOpsConfig.BASE_URL + "/"
                        + AzureDevOpsConfig.COLLECTION + "/"
                        + AzureDevOpsConfig.PROJECT
                        + "/_apis/git/repositories/"
                        + AzureDevOpsConfig.REPO_ID
                        + "/items"
                        + "?path=" + encodedPath
                        + "&versionDescriptor.version=master"
                        + "&$format=text"
                        + "&api-version=3.0";

        System.out.println("Calling URL:");
        System.out.println(fileUrl);

        String javaSource = AzureDevOpsRestClient.getFileContent(fileUrl);

        System.out.println("======================================");
        System.out.println("FILE: " + filePath);
        System.out.println("======================================");
        System.out.println(javaSource);
    }
}
