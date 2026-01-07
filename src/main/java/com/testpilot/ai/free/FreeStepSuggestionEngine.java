package com.testpilot.ai.free;

import java.nio.file.Path;

public class FreeStepSuggestionEngine {

    public static String suggestStep(
            String gherkinStep,
            Path mainJavaPath
    ) {

        if (gherkinStep.toLowerCase().contains("api")) {
            return ApiStepGenerator.generate(gherkinStep, mainJavaPath);
        }

        return PageObjectStepGenerator.generate(gherkinStep, mainJavaPath);
    }


    public static String suggestStep(String gherkinStep) {

        if (gherkinStep.toLowerCase().contains("click")) {
            return generateClickStep(gherkinStep);
        }

        if (gherkinStep.toLowerCase().contains("see")) {
            return generateValidationStep(gherkinStep);
        }

        if (gherkinStep.toLowerCase().contains("api")) {
            return generateApiStep(gherkinStep);
        }

        return "// TODO: Implement step for -> " + gherkinStep;
    }

    private static String generateClickStep(String step) {
        return """
        @And("^%s$")
        public void clickAction() {
            // TODO: call pageObject.click()
        }
        """.formatted(step);
    }

    private static String generateValidationStep(String step) {
        return """
        @Then("^%s$")
        public void validateAction() {
            // TODO: add assertion
        }
        """.formatted(step);
    }

    private static String generateApiStep(String step) {
        return """
        @When("^%s$")
        public void apiAction() {
            // TODO: call API service
        }
        """.formatted(step);
    }
}
