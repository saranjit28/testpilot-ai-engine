package com.testpilot.ai.runner;

import com.testpilot.ai.azure.AzureDevOpsRestClient;
import com.testpilot.ai.config.AzureDevOpsConfig;
import org.json.JSONArray;
import org.json.JSONObject;

public class StepsFileLister {

    public static void main(String[] args) throws Exception {

        String listUrl =
                AzureDevOpsConfig.BASE_URL + "/"
                        + AzureDevOpsConfig.COLLECTION + "/"
                        + AzureDevOpsConfig.PROJECT
                        + "/_apis/git/repositories/"
                        + AzureDevOpsConfig.REPO_ID
                        + "/items"
                        + "?scopePath=/src/test/java/com/automation/ospi/steps"
                        + "&recursionLevel=Full"
                        + "&api-version=7.0";

        String response = AzureDevOpsRestClient.getFileContent(listUrl);

        JSONObject json = new JSONObject(response);
        JSONArray items = json.getJSONArray("value");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            String path = item.getString("path");

            if (path.endsWith("Steps.java")) {
                System.out.println("FOUND: " + path);
                fetchAndPrintFile(path);
            }
        }
    }

    private static void fetchAndPrintFile(String filePath) throws Exception {

        String fileUrl =
                AzureDevOpsConfig.BASE_URL + "/"
                        + AzureDevOpsConfig.COLLECTION + "/"
                        + AzureDevOpsConfig.PROJECT
                        + "/_apis/git/repositories/"
                        + AzureDevOpsConfig.REPO_ID
                        + "/items"
                        + "?path=" + filePath
                        + "&versionDescriptor.version=master"
                        + "&includeContent=true"
                        + "&api-version=7.0";

        String response = AzureDevOpsRestClient.getFileContent(fileUrl);

        JSONObject fileJson = new JSONObject(response);
        String content = fileJson.getString("content");

        System.out.println("======================================");
        System.out.println("FILE: " + filePath);
        System.out.println("======================================");
        System.out.println(content);
    }
}