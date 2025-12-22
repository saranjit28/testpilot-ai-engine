package com.testpilot.ai.runner;

import com.testpilot.ai.ai.free.PageObjectStepGenerator;
import com.testpilot.ai.ai.free.ApiStepGenerator;

import java.nio.file.Path;
import java.util.List;

public class FullAITestRunner {

    public static void main(String[] args) {

        List<String> gherkinSteps = List.of(
                "Then I click login button",
                "When user submits grant application api"
        );

        Path mainJava =
                Path.of("D:/New Project/OSPI/govgrants-ospi-automation/src/main/java");

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
