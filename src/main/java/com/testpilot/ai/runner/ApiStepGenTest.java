package com.testpilot.ai.runner;

import com.testpilot.ai.ai.free.ApiStepGenerator;
import com.testpilot.ai.azure.AzureRepoResolver;
import com.testpilot.ai.config.AzureDevOpsConfig;
import com.testpilot.ai.tfs.AzureWorkspaceResolver;

import java.nio.file.Path;

public class ApiStepGenTest {

    public static void main(String[] args) throws Exception {

        String apiStep =
                "user submits grant application api";

        String generated =
                ApiStepGenerator.generate(
                        apiStep,
                        AzureWorkspaceResolver.getSrcMainJava("govgrants-ospi-automation"));

        System.out.println("\n🔥 GENERATED API STEP\n");
        System.out.println(generated);
    }
}
