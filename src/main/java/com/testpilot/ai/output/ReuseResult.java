package com.testpilot.ai.output;

public class ReuseResult {

    private final String stepText;
    private final double confidence;
    private final String filePath;
    private final String methodName;

    public ReuseResult(
            String stepText,
            double confidence,
            String filePath,
            String methodName
    ) {
        this.stepText = stepText;
        this.confidence = confidence;
        this.filePath = filePath;
        this.methodName = methodName;
    }

    public String getStepText() {
        return stepText;
    }

    public double getConfidence() {
        return confidence;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getMethodName() {
        return methodName;
    }
}