package com.testpilot.ai.output;

public class GenerateRequest {

    private final String stepText;
    private final double confidence;

    public GenerateRequest(
            String stepText,
            double confidence
    ) {
        this.stepText = stepText;
        this.confidence = confidence;
    }

    public String getStepText() {
        return stepText;
    }

    public double getConfidence() {
        return confidence;
    }
}