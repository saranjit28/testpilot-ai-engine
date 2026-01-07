package com.testpilot.ai.output.view;

public class ReuseView {

    private final String step;
    private final String confidence;
    private final String filePath;
    private final String method;

    public ReuseView(
            String step,
            String confidence,
            String filePath,
            String method
    ) {
        this.step = step;
        this.confidence = confidence;
        this.filePath = filePath;
        this.method = method;
    }

    public String getStep() {
        return step;
    }

    public String getConfidence() {
        return confidence;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getMethod() {
        return method;
    }
}
