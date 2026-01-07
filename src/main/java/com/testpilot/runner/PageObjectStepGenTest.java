package com.testpilot.runner;

import com.testpilot.ai.free.PageObjectStepGenerator;
import com.testpilot.tfs.AzureWorkspaceResolver;

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
