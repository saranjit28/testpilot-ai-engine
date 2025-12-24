package com.testpilot.ai.runner;

import com.testpilot.ai.ai.free.PageObjectStepGenerator;
import com.testpilot.ai.azure.AzureRepoResolver;
import com.testpilot.ai.config.AzureDevOpsConfig;
import com.testpilot.ai.tfs.AzureWorkspaceResolver;

import java.nio.file.Path;

public class PageObjectStepGenTest {

    public static void main(String[] args) throws Exception {

        String missingStep =
                "Then I click login button";

        String generated =
                PageObjectStepGenerator.generate(
                        missingStep,
                        AzureWorkspaceResolver.getSrcMainJava("govgrants-ospi-automation"));

        System.out.println("\n🔥 GENERATED STEP\n");
        System.out.println(generated);
    }
}
