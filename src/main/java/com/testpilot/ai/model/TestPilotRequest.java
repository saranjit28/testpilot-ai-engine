package com.testpilot.ai.model;

public class TestPilotRequest {

    private String manualTestCase;
    private String project;

    public String getManualTestCase() {
        return manualTestCase;
    }

    public void setManualTestCase(String manualTestCase) {
        this.manualTestCase = manualTestCase;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    private String module;

    // getters & setters
}