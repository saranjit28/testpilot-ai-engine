package com.testpilot.ai.link;

import com.testpilot.config.AzureDevOpsConfig;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AzureDevOpsLinkBuilder {

    public static String build(
            String repoName,
            String filePath
    ) {

        String encodedPath =
                URLEncoder.encode(
                        "/" + filePath.replace("\\", "/"),
                        StandardCharsets.UTF_8
                );

        return AzureDevOpsConfig.repoWebBaseUrl()
                + repoName
                + "?path="
                + encodedPath;
    }
}
