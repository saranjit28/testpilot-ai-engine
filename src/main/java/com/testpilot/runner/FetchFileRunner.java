package com.testpilot.runner;

import com.testpilot.azure.AzureDevOpsRestClient;

public class FetchFileRunner {

    public static void main(String[] args) throws Exception {

        String fileUrl =
                "https://teamservices.reisystems.com/tfs/SaaS/GovGrants%20Automation"
                + "/_apis/git/repositories/19d1f9ad-2649-4e42-b358-f69cc93bf20e/items"
                + "?path=/src/test/java/com/automation/ospi/steps/GenericStep.java";
//                + "&versionDescriptor.version=master"
//                + "&includeContent=true";
//                        + "&api-version=3.0";

        System.out.println("FETCHING JAVA FILE CONTENT...");
        System.out.println("----------------------------------");

        String response = AzureDevOpsRestClient.getFileContent(fileUrl);

        System.out.println("RAW RESPONSE:");
        System.out.println("----------------------------------");
        System.out.println(response);
    }
}
