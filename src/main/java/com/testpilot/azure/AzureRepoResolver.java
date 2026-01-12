package com.testpilot.azure;

import com.testpilot.config.AzureDevOpsConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AzureRepoResolver {

    private static String cachedRepoId;

    public static String resolveRepoId() throws Exception {

        if (cachedRepoId != null) {
            return cachedRepoId;
        }

        // ✅ Encode project name AND force %20 instead of +
        String encodedProject =
                URLEncoder.encode(
                        AzureDevOpsConfig.PROJECT,
                        StandardCharsets.UTF_8
                ).replace("+", "%20");

        String url =
                AzureDevOpsConfig.BASE_URL + "/"
                        + AzureDevOpsConfig.COLLECTION + "/"
                        + encodedProject
                        + "/_apis/git/repositories"
                        + "?api-version=3.0";

        System.out.println("🔗 Resolving repoId using URL = " + url);

        String response = AzureDevOpsRestClient.get(url);

        JSONArray repos =
                new JSONObject(response).getJSONArray("value");

        for (int i = 0; i < repos.length(); i++) {

            JSONObject repo = repos.getJSONObject(i);

            if (repo.getString("name")
                    .equalsIgnoreCase(AzureDevOpsConfig.REPOSITORY)) {

                cachedRepoId = repo.getString("id");

                System.out.println(
                        "✅ Repo resolved: "
                                + AzureDevOpsConfig.REPOSITORY
                                + " → " + cachedRepoId
                );

                return cachedRepoId;
            }
        }

        throw new RuntimeException(
                "Repository not found: "
                        + AzureDevOpsConfig.REPOSITORY
        );
    }
}