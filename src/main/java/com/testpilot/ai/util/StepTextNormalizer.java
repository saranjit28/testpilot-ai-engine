package com.testpilot.ai.util;

public final class StepTextNormalizer {

    private StepTextNormalizer() {}

    public static String normalize(String text) {
        if (text == null) return "";

        return text
                .toLowerCase()
                .replaceAll("^(given|when|then|and|but)\\s+", "")
                .replaceAll("\\^|\\$", "")
                .replaceAll("\"[^\"]*\"", "{string}")
                .replaceAll("\\\\", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
