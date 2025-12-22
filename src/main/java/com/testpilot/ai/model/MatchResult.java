package com.testpilot.ai.model;

public class MatchResult {

    private StepDefinition step;
    private double score;

    public MatchResult(StepDefinition step, double score) {
        this.step = step;
        this.score = score;
    }

    public StepDefinition getStep() {
        return step;
    }

    public double getScore() {
        return score;
    }
}