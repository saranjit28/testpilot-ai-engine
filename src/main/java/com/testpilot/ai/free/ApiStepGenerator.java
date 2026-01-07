package com.testpilot.ai.free;

import com.testpilot.ai.analyzer.ApiServiceScanner;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ApiStepGenerator {

    public static String generate(
            String gherkinStep,
            Path mainJavaPath
    ) {

        Map<String, List<String>> services =
                ApiServiceScanner.scan(mainJavaPath);

        for (Map.Entry<String, List<String>> entry : services.entrySet()) {

            String service = entry.getKey();

            for (String method : entry.getValue()) {

                if (gherkinStep.toLowerCase()
                        .contains(method.toLowerCase())) {

                    return buildApiStep(gherkinStep, service, method);
                }
            }
        }

        return defaultApiStep(gherkinStep);
    }

    private static String buildApiStep(
            String step,
            String service,
            String method
    ) {

        return """
        @When("^%s$")
        public void apiStep() {
            %s svc = new %s();
            svc.%s();
        }
        """.formatted(
                step,
                service,
                service,
                method
        );
    }

    private static String defaultApiStep(String step) {
        return """
        @When("^%s$")
        public void apiStep() {
            // TODO: call API
        }
        """.formatted(step);
    }
}
