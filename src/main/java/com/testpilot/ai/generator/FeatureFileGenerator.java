package com.testpilot.ai.generator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FeatureFileGenerator {

    public static String generateFeature(
            String testName,
            List<String> steps
    ) {

        StringBuilder sb = new StringBuilder();

        sb.append("Feature: ").append(testName).append("\n\n");
        sb.append("  Scenario: Auto generated scenario\n");

        for (String step : steps) {
            sb.append("    And ").append(step).append("\n");
        }

        return sb.toString();
    }

    public static String generateFileName() {
        return "generated_" + timestamp() + ".feature";
    }

    private static String timestamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
