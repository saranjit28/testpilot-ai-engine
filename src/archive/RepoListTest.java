package com.testpilot;
import com.testpilot.azure.AzureDevOpsRestClient;
import com.testpilot.config.AzureDevOpsConfig;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RepoListTest {

    public static void main(String[] args) throws Exception {



        String projectEncoded =
                URLEncoder.encode(AzureDevOpsConfig.PROJECT, StandardCharsets.UTF_8)
                        .replace("+", "%20");

        String url =
                AzureDevOpsConfig.BASE_URL + "/"
                        + AzureDevOpsConfig.COLLECTION + "/"
                        + projectEncoded
                        + "/_git/govgrants-ospi-automation";


        System.out.println("Calling: " + url);

        String response =
                AzureDevOpsRestClient.get(url);

        System.out.println(response);
    }
}

