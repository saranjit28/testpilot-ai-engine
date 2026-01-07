package com.testpilot.ai.similarity;

import java.util.Set;

public class KeywordOverlap {

    public static double score(Set<String> manual, Set<String> candidate) {

        if (manual == null || candidate == null || manual.isEmpty()) {
            return 0.0;
        }

        long common =
                manual.stream()
                        .filter(candidate::contains)
                        .count();

        return (double) common / manual.size();
    }
}