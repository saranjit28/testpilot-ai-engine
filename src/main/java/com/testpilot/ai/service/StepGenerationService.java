package com.testpilot.ai.service;

import org.springframework.stereotype.Service;

@Service
public class StepGenerationService {

    public String generateJavaStep(String gherkinStep) {

        String keyword = extractKeyword(gherkinStep);
        String stepText = removeKeyword(gherkinStep);
        String methodName = generateMethodName(stepText);

        return """
                @%s("^%s$")
                public void %s() {
                    // TODO: implement step logic
                }
                """.formatted(
                keyword,
                escapeQuotes(stepText),
                methodName
        );
    }

    private String extractKeyword(String step) {
        if (step.startsWith("Given")) return "Given";
        if (step.startsWith("When")) return "When";
        return "Then";
    }

    private String removeKeyword(String step) {
        return step.replaceFirst("^(Given|When|Then)\\s+", "");
    }

    private String generateMethodName(String step) {
        return "then_" + step
                .replaceAll("\"[^\"]*\"", "")
                .replaceAll("[^a-zA-Z ]", "")
                .trim()
                .replaceAll("\\s+", "_")
                .toLowerCase();
    }

    private String escapeQuotes(String text) {
        return text.replace("\"", "\\\"");
    }
}