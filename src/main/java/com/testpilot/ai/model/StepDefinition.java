package com.testpilot.ai.model;

public class StepDefinition {

    private String fileName;
    private String keyword;
    private String stepText;

    public String getFileName() {
        return fileName;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getStepText() {
        return stepText;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    @Override
    public String toString() {
        return keyword + " " + stepText + " [" + fileName + "]";
    }
}