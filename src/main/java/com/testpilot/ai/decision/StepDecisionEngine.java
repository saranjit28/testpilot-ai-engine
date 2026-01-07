package com.testpilot.ai.decision;

import com.testpilot.ai.index.KeywordIndex;
import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.similarity.SimilarityEngine;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class StepDecisionEngine {

    private static final double REUSE_THRESHOLD = 0.40;

    public static MatchResult decide(
            String manualStep,
            Set<String> manualKeywords,
            KeywordIndex index
    ) {

        List<StepDefinition> candidates =
                index.lookup(manualKeywords);

        if (candidates.isEmpty()) {
            return MatchResult.generate(0.0);
        }

        StepDefinition best = null;
        double bestScore = 0.0;

        for (StepDefinition candidate : candidates) {

            double score =
                    SimilarityEngine.compute(
                            manualStep,
                            manualKeywords,
                            candidate
                    );

            if (score > bestScore) {
                bestScore = score;
                best = candidate;
            }
        }

        if (best != null && bestScore >= REUSE_THRESHOLD) {
            return MatchResult.reuse(best, bestScore);
        }

        return MatchResult.generate(bestScore);
    }
}