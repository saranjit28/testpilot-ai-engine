package com.testpilot.runner;

import com.testpilot.azure.AzureDevOpsRestClient;
import com.testpilot.azure.AzureRepoResolver;
import com.testpilot.config.AzureDevOpsConfig;
import com.testpilot.parser.StepDefinitionParser;
import com.testpilot.ai.model.StepDefinition;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StepsFileLister {

    // ✅ MULTIPLE STEP LOCATIONS
    private static final String[] STEP_PATHS = {
            "/src/test/java/com/automation/ospi/steps",
            "/src/main/java/com/automation/rei/govgrantsSteps"
    };

    public static void main(String[] args) throws Exception {

        List<StepDefinition> allSteps = new ArrayList<>();

        for (String path : STEP_PATHS) {
            allSteps.addAll(fetchStepsFromPath(path));
        }

        System.out.println("\nTOTAL STEPS FOUND: " + allSteps.size());

        allSteps.forEach(System.out::println);
    }

    private static List<StepDefinition> fetchStepsFromPath(
            String scopePath
    ) throws Exception {

        List<StepDefinition> steps = new ArrayList<>();

        String listUrl =
                AzureDevOpsConfig.BASE_URL + "/"
                        + AzureDevOpsConfig.COLLECTION + "/"
                        + AzureDevOpsConfig.PROJECT
                        + "/_apis/git/repositories/"
                        + AzureRepoResolver.resolveRepoId(
                        "govgrants-ospi-automation")
                        + "/items"
                        + "?scopePath=" + scopePath
                        + "&recursionLevel=Full"
                        + "&api-version=3.0";

        String response = AzureDevOpsRestClient.get(listUrl);
        JSONArray items =
                new JSONObject(response).getJSONArray("value");

        for (int i = 0; i < items.length(); i++) {

            JSONObject item = items.getJSONObject(i);

            if (item.optBoolean("isFolder")) continue;

            String filePath = item.getString("path");

            if (!filePath.endsWith(".java")) continue;

            String fileContent =
                    AzureDevOpsRestClient.getFileContent(
                            buildFileUrl(filePath)
                    );

            steps.addAll(
                    StepDefinitionParser.parse(
                            fileContent,
                            filePath
                    )
            );
        }

        return steps;
    }

    private static String buildFileUrl(String filePath) throws Exception {
        return AzureDevOpsConfig.BASE_URL + "/"
                + AzureDevOpsConfig.COLLECTION + "/"
                + AzureDevOpsConfig.PROJECT
                + "/_apis/git/repositories/"
                + AzureRepoResolver.resolveRepoId(
                "govgrants-ospi-automation")
                + "/items"
                + "?path=" + filePath
                + "&$format=text"
                + "&api-version=3.0";
    }
}