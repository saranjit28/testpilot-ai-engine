package com.testpilot.ai.similarity;

import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.util.StepTextNormalizer;

public class SimilarityEngine {

    public static double score(String manualStep, StepDefinition candidate) {

        String manual =
                StepTextNormalizer.normalize(manualStep);

        String repo =
                StepTextNormalizer.normalize(candidate.getMatchText());

        if (manual.equals(repo)) {
            return 1.0;
        }

        double keywordScore = keywordSimilarity(manual, repo);
        double textScore = textSimilarity(manual, repo);

        return (keywordScore * 0.6) + (textScore * 0.4);
    }

    private static double keywordSimilarity(String a, String b) {
        String[] aw = a.split(" ");
        String[] bw = b.split(" ");

        int common = 0;
        for (String w1 : aw) {
            for (String w2 : bw) {
                if (w1.equals(w2)) common++;
            }
        }
        return aw.length == 0 ? 0 : (double) common / aw.length;
    }

    private static double textSimilarity(String a, String b) {
        int max = Math.max(a.length(), b.length());
        if (max == 0) return 1.0;

        int same = 0;
        for (int i = 0; i < Math.min(a.length(), b.length()); i++) {
            if (a.charAt(i) == b.charAt(i)) same++;
        }
        return (double) same / max;
    }
}