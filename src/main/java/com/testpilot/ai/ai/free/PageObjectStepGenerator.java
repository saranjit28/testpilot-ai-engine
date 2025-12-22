package com.testpilot.ai.ai.free;

import com.testpilot.ai.ai.analyzer.PageObjectScanner;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class PageObjectStepGenerator {

    public static String generate(
            String gherkinStep,
            Path mainJavaPath
    ) {

        Map<String, List<String>> pages =
                PageObjectScanner.scan(mainJavaPath);

        for (Map.Entry<String, List<String>> entry : pages.entrySet()) {

            String page = entry.getKey();

            for (String method : entry.getValue()) {

                if (gherkinStep.toLowerCase()
                        .contains(method.toLowerCase().replace("click", "")
                                .replace("enter", "")
                                .replace("validate", ""))) {

                    return buildStep(gherkinStep, page, method);
                }
            }
        }

        return defaultStep(gherkinStep);
    }

    private static String buildStep(
            String step,
            String page,
            String method
    ) {

        return """
        @Then("^%s$")
        public void stepImpl() {
            %s %s = new %s();
            %s.%s();
        }
        """.formatted(
                step,
                page, page.toLowerCase(),
                page,
                page.toLowerCase(),
                method
        );
    }

    private static String defaultStep(String step) {
        return """
        @Then("^%s$")
        public void stepImpl() {
            // TODO: implement
        }
        """.formatted(step);
    }
}
