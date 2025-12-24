package com.testpilot.ai.runner;

import com.testpilot.ai.ai.free.PageObjectStepGenerator;
import com.testpilot.ai.ai.free.ApiStepGenerator;
import com.testpilot.ai.azure.AzureRepoResolver;
import com.testpilot.ai.config.AzureDevOpsConfig;
import com.testpilot.ai.tfs.AzureWorkspaceResolver;

import java.nio.file.Path;
import java.util.List;

public class FullAITestRunner {

    public static void main(String[] args) throws Exception {

        List<String> gherkinSteps = List.of(
                "Then I click login button",
                "When user submits grant application api"
        );

        Path mainJava =
                AzureWorkspaceResolver.getSrcMainJava("govgrants-ospi-automation");

        for (String step : gherkinSteps) {

            String generated;

            if (step.toLowerCase().contains("api")) {
                generated = ApiStepGenerator.generate(step, mainJava);
            } else {
                generated = PageObjectStepGenerator.generate(step, mainJava);
            }

            System.out.println("\n================================");
            System.out.println("STEP: " + step);
            System.out.println(generated);
        }
    }
}
