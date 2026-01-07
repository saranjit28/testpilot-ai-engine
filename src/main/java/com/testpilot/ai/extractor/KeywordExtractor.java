package com.testpilot.ai.engine;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class KeywordExtractor {

    private static final Set<String> STOP_WORDS = Set.of(
            "the", "a", "an", "to", "on", "in", "with", "and", "or", "is", "are"
    );

    public static Set<String> extract(String step) {

        if (step == null || step.isBlank()) {
            return Set.of();
        }

        return Arrays.stream(
                        TextNormalizer.normalize(step).split(" ")
                )
                .filter(word -> word.length() > 2)
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.toSet());
    }
}