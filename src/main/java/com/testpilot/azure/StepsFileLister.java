package com.testpilot.azure;

import com.testpilot.ai.model.StepDefinition;
import com.testpilot.config.AzureDevOpsConfig;
import com.testpilot.parser.StepDefinitionParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StepsFileLister {

    private static final String[] STEP_PATHS = {
            "/src/test/java/com/automation/ospi/steps",
            "/src/main/java/com/automation/rei/govgrantsSteps"
    };

    public static List<StepDefinition> fetchAllSteps() throws Exception {

        List<StepDefinition> allSteps = new ArrayList<>();

        for (String path : STEP_PATHS) {
            System.out.println("📂 Fetching steps from: " + path);
            allSteps.addAll(fetchStepsFromPath(path));
        }

        System.out.println("📊 TOTAL STEPS FETCHED FROM TFS = " + allSteps.size());
        return allSteps;
    }

    private static List<StepDefinition> fetchStepsFromPath(String scopePath)
            throws Exception {

        List<StepDefinition> steps = new ArrayList<>();

        // ✅ Encode project name correctly (%20, not +)
        String encodedProject =
                URLEncoder.encode(
                        AzureDevOpsConfig.PROJECT,
                        StandardCharsets.UTF_8
                ).replace("+", "%20");

        // ✅ Encode scopePath
        String encodedScopePath =
                URLEncoder.encode(
                        scopePath,
                        StandardCharsets.UTF_8
                );

        String listUrl =
                AzureDevOpsConfig.BASE_URL + "/"
                        + AzureDevOpsConfig.COLLECTION + "/"
                        + encodedProject
                        + "/_apis/git/repositories/"
                        + AzureRepoResolver.resolveRepoId()
                        + "/items"
                        + "?scopePath=" + encodedScopePath
                        + "&recursionLevel=Full"
                        + "&api-version=3.0";

        System.out.println("🔗 LIST URL = " + listUrl);

        String response = AzureDevOpsRestClient.get(listUrl);

        JSONArray items =
                new JSONObject(response).getJSONArray("value");

        for (int i = 0; i < items.length(); i++) {

            JSONObject item = items.getJSONObject(i);

            if (item.optBoolean("isFolder")) continue;

            String filePath = item.getString("path");

            if (!filePath.endsWith(".java")) continue;

            System.out.println("📄 Parsing file: " + filePath);

            String fileContent =
                    AzureDevOpsRestClient.getFileContent(
                            buildFileUrl(filePath, encodedProject)
                    );

            steps.addAll(
                    StepDefinitionParser.parse(
                            fileContent,
                            filePath
                    )
            );
        }

        System.out.println("✅ Steps found under " + scopePath + " = " + steps.size());
        return steps;
    }

    private static String buildFileUrl(
            String filePath,
            String encodedProject
    ) throws Exception {

        String encodedFilePath =
                URLEncoder.encode(
                        filePath,
                        StandardCharsets.UTF_8
                );

        return AzureDevOpsConfig.BASE_URL + "/"
                + AzureDevOpsConfig.COLLECTION + "/"
                + encodedProject
                + "/_apis/git/repositories/"
                + AzureRepoResolver.resolveRepoId()
                + "/items"
                + "?path=" + encodedFilePath
                + "&$format=text"
                + "&api-version=3.0";
    }
}