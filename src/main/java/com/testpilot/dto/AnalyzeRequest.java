package com.testpilot.dto;

import java.util.List;

public class AnalyzeRequest {

    private String requestId;
    private String testName;
    private List<String> steps;

    // ✅ REQUIRED by Jackson
    public AnalyzeRequest() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<String> getSteps() {
        return steps;
    }

    // ✅ THIS WAS MISSING — ROOT CAUSE
    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}