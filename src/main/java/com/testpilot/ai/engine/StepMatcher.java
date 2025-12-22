package com.testpilot.ai.engine;

import com.testpilot.ai.model.StepDefinition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.testpilot.ai.engine.StepSimilarityUtil.similarity;

public class StepMatcher {

    private static final double CONFIDENCE_THRESHOLD = 0.70;

    public static List<StepMatch> findMatches(
            String gherkinStep,
            List<StepDefinition> repoSteps
    ) {

        List<StepMatch> matches = new ArrayList<>();

        String normalizedInput = normalize(gherkinStep);

        for (StepDefinition step : repoSteps) {

            String normalizedRepoStep =
                    normalize(step.getStepText());

            double score =
                    similarity(normalizedInput, normalizedRepoStep);

            if (score >= CONFIDENCE_THRESHOLD) {
                matches.add(new StepMatch(step, score));
            }
        }

        matches.sort(
                Comparator.comparingDouble(StepMatch::getConfidence)
                        .reversed()
        );

        return matches;
    }

    // 🔥 NORMALIZATION (VERY IMPORTANT)
    private static String normalize(String step) {
        return step
                .replaceAll("\"[^\"]*\"", "{string}") // quoted text
                .replaceAll("\\d+", "{string}")       // numbers
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase();
    }

    public static class StepMatch {

        private final StepDefinition step;
        private final double confidence;

        public StepMatch(
                StepDefinition step,
                double confidence
        ) {
            this.step = step;
            this.confidence = confidence;
        }

        public StepDefinition getStep() {
            return step;
        }

        public double getConfidence() {
            return confidence;
        }
    }
}