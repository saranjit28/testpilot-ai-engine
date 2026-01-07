package com.testpilot.ai.output.view;

public class GenerateView {

    private final String step;
    private final String confidence;

    public GenerateView(
            String step,
            String confidence
    ) {
        this.step = step;
        this.confidence = confidence;
    }

    public String getStep() {
        return step;
    }

    public String getConfidence() {
        return confidence;
    }
}