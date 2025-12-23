package com.testpilot.ai.util;

public class AiOutputValidator {

    public static boolean isValidGherkinStep(String text) {

        if (text == null || text.isBlank()) return false;

        String normalized = text.trim();

        // ❌ Reject compiler / runtime errors
        if (normalized.toLowerCase().contains("error")
                || normalized.toLowerCase().contains("exception")
                || normalized.toLowerCase().contains("invalid character")
                || normalized.contains("\\n")
                || normalized.contains("{")
                || normalized.contains("}")) {
            return false;
        }

        // ✅ Must start with Gherkin keyword
        return normalized.startsWith("Given ")
                || normalized.startsWith("When ")
                || normalized.startsWith("Then ")
                || normalized.startsWith("And ");
    }
}
