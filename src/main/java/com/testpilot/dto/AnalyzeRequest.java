package com.testpilot.dto;

import java.util.List;

public class AnalyzeRequest {

    private String requestId;
    private String testName;

    // Manual steps coming from Quick Script UI
    private List<String> manualTestCase;

    /* ---------- Controller-facing ---------- */

    public String getRequestId() {
        return requestId;
    }

    public String getTestName() {
        return testName;
    }

    public List<String> getManualTestCase() {
        return manualTestCase;
    }

    /* ---------- Service / Engine-facing ---------- */

    /**
     * Alias for internal processing.
     * Both return the same manual steps.
     */
    public List<String> getSteps() {
        return manualTestCase;
    }
}