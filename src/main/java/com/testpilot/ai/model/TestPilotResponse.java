package com.testpilot.ai.model;

import com.testpilot.ai.output.view.GenerateView;
import com.testpilot.ai.output.view.ReuseView;
import com.testpilot.ai.output.view.SummaryView;

import java.util.List;

public class TestPilotResponse {

    private final String requestId;
    private final String testName;
    private final String status;
    private final SummaryView summary;
    private final List<ReuseView> reusedSteps;
    private final List<GenerateView> generatedSteps;
    private final List<String> messages;

    private TestPilotResponse(
            String requestId,
            String testName,
            String status,
            SummaryView summary,
            List<ReuseView> reusedSteps,
            List<GenerateView> generatedSteps,
            List<String> messages
    ) {
        this.requestId = requestId;
        this.testName = testName;
        this.status = status;
        this.summary = summary;
        this.reusedSteps = reusedSteps;
        this.generatedSteps = generatedSteps;
        this.messages = messages;
    }

    public static TestPilotResponse build(
            String requestId,
            String testName,
            String status,
            List<ReuseView> reusedSteps,
            List<GenerateView> generatedSteps,
            List<String> messages
    ) {

        SummaryView summary =
                new SummaryView(
                        reusedSteps.size() + generatedSteps.size(),
                        reusedSteps.size(),
                        generatedSteps.size()
                );

        return new TestPilotResponse(
                requestId,
                testName,
                status,
                summary,
                reusedSteps,
                generatedSteps,
                messages
        );
    }

    public String getRequestId() {
        return requestId;
    }

    public String getTestName() {
        return testName;
    }

    public String getStatus() {
        return status;
    }

    public SummaryView getSummary() {
        return summary;
    }

    public List<ReuseView> getReusedSteps() {
        return reusedSteps;
    }

    public List<GenerateView> getGeneratedSteps() {
        return generatedSteps;
    }

    public List<String> getMessages() {
        return messages;
    }
}