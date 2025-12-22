package com.testpilot.ai.runner;

import com.testpilot.ai.ai.free.PageObjectStepGenerator;

import java.nio.file.Path;

public class PageObjectStepGenTest {

    public static void main(String[] args) {

        String missingStep =
                "Then I click login button";

        String generated =
                PageObjectStepGenerator.generate(
                        missingStep,
                        Path.of("D:/New Project/OSPI/govgrants-ospi-automation/src/main/java")
                );

        System.out.println("\n🔥 GENERATED STEP\n");
        System.out.println(generated);
    }
}
