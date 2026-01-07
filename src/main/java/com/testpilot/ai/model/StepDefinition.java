package com.testpilot.ai.model;

import java.nio.file.Path;
import java.util.Set;

public class StepDefinition {

    private final String stepText;
    private final String matchText;
    private final String filePath;
    private final String methodName;
    private final Set<String> keywords;

    public StepDefinition(
            String stepText,
            String matchText,
            String filePath,
            String methodName,
            Set<String> keywords
    ) {
        this.stepText = stepText;
        this.matchText = matchText;
        this.filePath = filePath;
        this.methodName = methodName;
        this.keywords = keywords;
    }

    public String getStepText() {
        return stepText;
    }

    public String getMatchText() {
        return matchText;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getMethodName() {
        return methodName;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    // derived, not stored
    public String getFileName() {
        return Path.of(filePath).getFileName().toString();
    }
}