package com.testpilot.ai.page;

public class PageInferenceResult {

    private final String pageName;
    private final String action;

    public PageInferenceResult(
            String pageName,
            String action
    ) {
        this.pageName = pageName;
        this.action = action;
    }

    public String getPageName() {
        return pageName;
    }

    public String getAction() {
        return action;
    }
}