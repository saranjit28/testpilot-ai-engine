package com.testpilot.runner;

import com.testpilot.azure.AzureDevOpsRestClient;
import com.testpilot.azure.AzureRepoResolver;
import com.testpilot.config.AzureDevOpsConfig;
import com.testpilot.export.StepJsonExporter;
import com.testpilot.ai.model.StepDefinition;
import com.testpilot.parser.StepDefinitionParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StepExportRunner {

    public static void main(String[] args) throws Exception {

        List<StepDefinition> allSteps = new ArrayList<>();

        String listUrl =
                AzureDevOpsConfig.BASE_URL + "/"
                        + AzureDevOpsConfig.COLLECTION + "/"
                        + AzureDevOpsConfig.PROJECT
                        + "/_apis/git/repositories/"
                        + AzureRepoResolver.resolveRepoId(
                        "govgrants-ospi-automation")
                        + "/items"
                        + "?scopePath=/src/test/java"
                        + "&recursionLevel=Full"
                        + "&api-version=3.0";

        String response = AzureDevOpsRestClient.get(listUrl);
        JSONArray items = new JSONObject(response).getJSONArray("value");

        for (int i = 0; i < items.length(); i++) {

            JSONObject item = items.getJSONObject(i);
            if (item.optBoolean("isFolder")) continue;

            String path = item.getString("path");
            if (!path.endsWith("Steps.java")) continue;

            String encodedPath =
                    URLEncoder.encode(path, StandardCharsets.UTF_8);

            String fileUrl =
                    AzureDevOpsConfig.BASE_URL + "/"
                            + AzureDevOpsConfig.COLLECTION + "/"
                            + AzureDevOpsConfig.PROJECT
                            + "/_apis/git/repositories/"
                            + AzureRepoResolver.resolveRepoId(
                            "govgrants-ospi-automation")
                            + "/items"
                            + "?path=" + encodedPath
                            + "&versionDescriptor.version=master"
                            + "&$format=text"
                            + "&api-version=3.0";

            String javaSource =
                    AzureDevOpsRestClient.getFileContent(fileUrl);

            allSteps.addAll(
                    StepDefinitionParser.parse(javaSource, path)
            );
        }

        StepJsonExporter.export(allSteps, "steps-output.json");

        System.out.println("Steps exported to steps-output.json");
    }
}
