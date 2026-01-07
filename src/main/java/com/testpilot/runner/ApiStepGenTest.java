package com.testpilot.runner;

import com.testpilot.ai.free.ApiStepGenerator;
import com.testpilot.tfs.AzureWorkspaceResolver;

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
