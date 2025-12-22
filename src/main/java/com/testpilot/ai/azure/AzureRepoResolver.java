package com.testpilot.ai.azure;

import com.testpilot.ai.config.AzureDevOpsConfig;
import org.json.JSONArray;
import org.json.JSONObject;

public class AzureRepoResolver {

    public static String resolveRepoId(String repoName)
            throws Exception {

        String url =
                AzureDevOpsConfig.BASE_URL + "/"
                        + AzureDevOpsConfig.COLLECTION + "/"
                        + AzureDevOpsConfig.PROJECT
                        + "/_apis/git/repositories"
                        + "?api-version=3.0";

        String response =
                AzureDevOpsRestClient.get(url);

        JSONArray repos =
                new JSONObject(response).getJSONArray("value");

        for (int i = 0; i < repos.length(); i++) {

            JSONObject repo = repos.getJSONObject(i);

            if (repo.getString("name")
                    .equalsIgnoreCase(repoName)) {

                return repo.getString("id");
            }
        }

        throw new RuntimeException(
                "Repository not found: " + repoName
        );
    }
}