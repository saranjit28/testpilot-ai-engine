package com.testpilot.ai.output.view;

public class SummaryView {

    private final int totalSteps;
    private final int reused;
    private final int generated;

    public SummaryView(
            int totalSteps,
            int reused,
            int generated
    ) {
        this.totalSteps = totalSteps;
        this.reused = reused;
        this.generated = generated;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public int getReused() {
        return reused;
    }

    public int getGenerated() {
        return generated;
    }
}