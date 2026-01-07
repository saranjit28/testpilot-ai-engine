package com.testpilot.ai.codegen;

public class JavaStepGenerator {

    public static String generate(String gherkin) {

        return """
        @%s("%s")
        public void stepDefinition() {
            // TODO: implement
        }
        """.formatted(
                gherkin.split(" ")[0],
                gherkin.substring(gherkin.indexOf(" ") + 1)
        );
    }
}
