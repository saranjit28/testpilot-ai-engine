package com.testpilot.config;

public final class AzureDevOpsConfig {

    public static final String BASE_URL =
            "https://teamservices.reisystems.com/tfs";
    public static final String COLLECTION = "SaaS";

    public static final String PROJECT = "GovGrants Automation";

    public static final String REPOSITORY =
            "govgrants-ospi-automation";

    public static final String PAT = "mvztaaqsgcj6uo65oqjoka7rivzfrhypnyvb564o5kfhqtxjz3rq";

    private AzureDevOpsConfig() {
    }

    public static String repoWebBaseUrl() {
        return BASE_URL + "/"
                + COLLECTION + "/"
                + PROJECT
                + "/_git/"
                + REPOSITORY;
    }
}

