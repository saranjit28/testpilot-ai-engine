package com.testpilot.ai.similarity;

import java.util.Set;

public class SimilarityUtil {

    // Jaccard similarity for keyword sets
    public static double keywordSimilarity(
            Set<String> a,
            Set<String> b
    ) {
        if (a.isEmpty() || b.isEmpty()) return 0.0;

        int intersection = 0;
        for (String s : a) {
            if (b.contains(s)) intersection++;
        }

        int union = a.size() + b.size() - intersection;
        return (double) intersection / union;
    }

    // Simple normalized string similarity
    public static double textSimilarity(
            String a,
            String b
    ) {
        if (a == null || b == null) return 0.0;

        a = a.toLowerCase();
        b = b.toLowerCase();

        int matches = 0;
        int min = Math.min(a.length(), b.length());

        for (int i = 0; i < min; i++) {
            if (a.charAt(i) == b.charAt(i)) {
                matches++;
            }
        }

        return (double) matches / Math.max(a.length(), b.length());
    }
}