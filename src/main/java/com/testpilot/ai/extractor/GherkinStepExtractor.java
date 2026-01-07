package com.testpilot.ai.extractor;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class GherkinStepExtractor {

    private static final List<String> GHERKIN_KEYWORDS =
            List.of("Given", "When", "Then", "And", "But");

    public static List<String> extractSteps(Path repoRoot) {

        try {
            return Files.walk(repoRoot)
                    .filter(path -> path.toString().endsWith(".feature"))
                    .flatMap(GherkinStepExtractor::readFeatureFile)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to scan feature files", e);
        }
    }

    private static java.util.stream.Stream<String> readFeatureFile(Path featureFile) {

        try {
            return Files.lines(featureFile)
                    .map(String::trim)
                    .filter(GherkinStepExtractor::isStepLine)
                    .map(GherkinStepExtractor::stripKeyword);

        } catch (IOException e) {
            return java.util.stream.Stream.empty();
        }
    }

    private static boolean isStepLine(String line) {
        return GHERKIN_KEYWORDS.stream().anyMatch(line::startsWith);
    }

    private static String stripKeyword(String line) {
        for (String keyword : GHERKIN_KEYWORDS) {
            if (line.startsWith(keyword)) {
                return line.substring(keyword.length()).trim();
            }
        }
        return line;
    }
}
