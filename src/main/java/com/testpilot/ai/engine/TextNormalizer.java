package com.testpilot.ai.engine;

public class TextNormalizer {

    private TextNormalizer() {
        // utility class
    }

    public static String normalize(String input) {

        if (input == null) {
            return "";
        }

        return input
                .toLowerCase()
                .replaceAll("\"[^\"]*\"", "")
                .replaceAll("[^a-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
