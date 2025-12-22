package com.testpilot.ai.runner;

import com.testpilot.ai.ai.free.ApiStepGenerator;

import java.nio.file.Path;

public class ApiStepGenTest {

    public static void main(String[] args) {

        String apiStep =
                "When user submits grant application api";

        String generated =
                ApiStepGenerator.generate(
                        apiStep,
                        Path.of("D:/New Project/OSPI/govgrants-ospi-automation/src/main/java")
                );

        System.out.println("\n🔥 GENERATED API STEP\n");
        System.out.println(generated);
    }
}
