package com.testpilot.ai.decision;

import com.testpilot.ai.model.StepDefinition;

public class MatchResult {

    private final boolean reusable;
    private final double confidence;
    private final StepDefinition matchedStep;

    private MatchResult(
            boolean reusable,
            double confidence,
            StepDefinition matchedStep
    ) {
        this.reusable = reusable;
        this.confidence = confidence;
        this.matchedStep = matchedStep;
    }

    public static MatchResult reuse(
            StepDefinition step,
            double confidence
    ) {
        return new MatchResult(true, confidence, step);
    }

    public static MatchResult generate(double confidence) {
        return new MatchResult(false, confidence, null);
    }

    public boolean isReusable() {
        return reusable;
    }

    public double getConfidence() {
        return confidence;
    }

    public StepDefinition getMatchedStep() {
        return matchedStep;
    }
}