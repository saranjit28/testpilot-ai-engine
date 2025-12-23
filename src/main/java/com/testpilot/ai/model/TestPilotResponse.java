package com.testpilot.ai.model;

import com.testpilot.ai.engine.StepMatcher;
import java.util.List;

public class TestPilotResponse {

    private String status;
    private String message;
    private String originalText;

    private List<String> gherkinSteps;
    private List<StepMatcher.StepMatch> matchedSteps;
    private List<String> missingSteps;
    private List<String> errorMessages;

    // ---------- Getters ----------

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getOriginalText() {
        return originalText;
    }

    public List<String> getGherkinSteps() {
        return gherkinSteps;
    }

    public List<StepMatcher.StepMatch> getMatchedSteps() {
        return matchedSteps;
    }

    public List<String> getMissingSteps() {
        return missingSteps;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    // ---------- Setters ----------

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public void setGherkinSteps(List<String> gherkinSteps) {
        this.gherkinSteps = gherkinSteps;
    }

    public void setMatchedSteps(List<StepMatcher.StepMatch> matchedSteps) {
        this.matchedSteps = matchedSteps;
    }

    public void setMissingSteps(List<String> missingSteps) {
        this.missingSteps = missingSteps;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    // ---------- Factory Builder (SAFE) ----------

    public static TestPilotResponse build(
            String status,
            String message,
            String originalText,
            List<String> gherkinSteps,
            List<StepMatcher.StepMatch> matchedSteps,
            List<String> missingSteps,
            List<String> errorMessages
    ) {
        TestPilotResponse res = new TestPilotResponse();
        res.status = status;
        res.message = message;
        res.originalText = originalText;
        res.gherkinSteps = gherkinSteps;
        res.matchedSteps = matchedSteps;
        res.missingSteps = missingSteps;
        res.errorMessages = errorMessages;
        return res;
    }
}