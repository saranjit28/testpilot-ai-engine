package com.testpilot.ai.similarity;

import com.testpilot.ai.engine.TextNormalizer;
import com.testpilot.ai.model.StepDefinition;

import java.util.Set;

public class SimilarityEngine {

    private static final double TEXT_WEIGHT = 0.6;
    private static final double KEYWORD_WEIGHT = 0.4;

    public static double compute(
            String manualStep,
            Set<String> manualKeywords,
            StepDefinition candidate
    ) {

        String normalizedManual =
                TextNormalizer.normalize(manualStep);

        double textScore =
                TokenSimilarity.score(
                        normalizedManual,
                        candidate.getMatchText()
                );

        double keywordScore =
                KeywordOverlap.score(
                        manualKeywords,
                        candidate.getKeywords()
                );

        return (TEXT_WEIGHT * textScore)
                + (KEYWORD_WEIGHT * keywordScore);
    }
}
