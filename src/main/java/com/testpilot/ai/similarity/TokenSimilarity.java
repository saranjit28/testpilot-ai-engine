package com.testpilot.ai.similarity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TokenSimilarity {

    public static double score(String a, String b) {

        if (a == null || b == null || a.isBlank() || b.isBlank()) {
            return 0.0;
        }

        Set<String> tokensA = tokenize(a);
        Set<String> tokensB = tokenize(b);

        Set<String> intersection = new HashSet<>(tokensA);
        intersection.retainAll(tokensB);

        Set<String> union = new HashSet<>(tokensA);
        union.addAll(tokensB);

        if (union.isEmpty()) {
            return 0.0;
        }

        return (double) intersection.size() / union.size();
    }

    private static Set<String> tokenize(String text) {
        return new HashSet<>(Arrays.asList(text.split(" ")));
    }
}