package com.testpilot.ai;
import com.testpilot.ai.azure.AzureDevOpsRestClient;
import com.testpilot.ai.config.AzureDevOpsConfig;

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

//        https://teamservices.reisystems.com/tfs/SaaS/GovGrants%20Automation/_git/govgrants-ospi-automation?path=%2Fsrc%2Ftest%2Fjava%2Fcom%2Fautomation%2Fospi%2Fsteps%2FGenericStep.java&version=GBmaster&_a=contents

        System.out.println("Calling: " + url);

        String response =
                AzureDevOpsRestClient.get(url, AzureDevOpsConfig.PAT);

        System.out.println(response);
    }
}

