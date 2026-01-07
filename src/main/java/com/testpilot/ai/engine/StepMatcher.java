package com.testpilot.ai.engine;

import com.testpilot.ai.model.StepDefinition;

import java.util.*;

public class StepMatcher {

    private static final double CONFIDENCE_THRESHOLD = 0.40;

    public static List<StepMatch> findMatches(
            String gherkinStep,
            List<StepDefinition> repoSteps
    ) {

        List<StepMatch> matches = new ArrayList<>();

        if (gherkinStep == null || repoSteps == null) {
            return matches;
        }

        String normalizedInput = normalizeInput(gherkinStep);

        for (StepDefinition step : repoSteps) {

            if (step.getMatchText() == null || step.getMatchText().isBlank()) {
                continue;
            }

            double score =
                    StepSimilarityUtil.similarity(
                            normalizedInput,
                            step.getMatchText()
                    );

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

    private static String normalizeInput(String input) {
        return input
                .replaceAll("\"[^\"]*\"", "{string}")
                .replaceAll("\\d+", "{string}")
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