package com.testpilot.ai.engine;

import com.testpilot.ai.model.StepDefinition;

public class MatchResult {

    private final StepDefinition step;
    private final double confidence;

    public MatchResult(StepDefinition step, double confidence) {
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