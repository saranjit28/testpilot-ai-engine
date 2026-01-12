package com.testpilot.ai.engine;

import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.similarity.SimilarityEngine;

import java.util.List;

public class StepMatcher {

    public static MatchResult findBestMatch(
            String manualStep,
            List<StepDefinition> repoSteps
    ) {

        MatchResult best = null;

        for (StepDefinition step : repoSteps) {

            double score =
                    SimilarityEngine.score(manualStep, step);

            if (best == null || score > best.getConfidence()) {
                best = new MatchResult(step, score);
            }
        }

        return best;
    }
}