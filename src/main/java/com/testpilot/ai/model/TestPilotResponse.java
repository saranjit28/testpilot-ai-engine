package com.testpilot.ai.model;

import com.testpilot.ai.engine.StepMatcher;

import java.util.List;

public class TestPilotResponse {

    private List<String> gherkinSteps;
    private List<StepMatcher.StepMatch> matchedSteps;
    private List<String> missingSteps;

    public static TestPilotResponse build(
            List<String> gherkinSteps,
            List<StepMatcher.StepMatch> matchedSteps,
            List<String> missingSteps) {

        TestPilotResponse res = new TestPilotResponse();
        res.gherkinSteps = gherkinSteps;
        res.matchedSteps = matchedSteps;
        res.missingSteps = missingSteps;
        return res;
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
}