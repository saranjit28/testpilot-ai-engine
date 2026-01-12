package com.testpilot.ai.model;

import java.util.Set;

public class StepDefinition {

    private final String keyword;
    private final String stepText;
    private final String matchText;
    private final String filePath;
    private final String methodName;
    private final Set<String> keywords;

    /* =====================================================
       PRIMARY (NEW) CONSTRUCTOR – USE THIS GOING FORWARD
       ===================================================== */
    public StepDefinition(
            String keyword,
            String stepText,
            String matchText,
            String filePath,
            String methodName,
            Set<String> keywords
    ) {
        this.keyword = keyword;
        this.stepText = stepText;
        this.matchText = matchText;
        this.filePath = filePath;
        this.methodName = methodName;
        this.keywords = keywords;
    }

    /* =====================================================
       BACKWARD-COMPATIBLE CONSTRUCTOR (DO NOT REMOVE)
       Supports existing code without modification
       ===================================================== */
    public StepDefinition(
            String stepText,
            String matchText,
            String filePath,
            String methodName,
            Set<String> keywords
    ) {
        this.keyword = inferKeyword(stepText);
        this.stepText = stepText;
        this.matchText = matchText;
        this.filePath = filePath;
        this.methodName = methodName;
        this.keywords = keywords;
    }

    /* =========================
       GETTERS (REQUIRED)
       ========================= */

    public String getKeyword() {
        return keyword;
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

    public String getFileName() {
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    public String getMethodName() {
        return methodName;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    /* =========================
       INTERNAL HELPERS
       ========================= */

    private static String inferKeyword(String stepText) {
        if (stepText == null) return "And";
        if (stepText.startsWith("Given")) return "Given";
        if (stepText.startsWith("When")) return "When";
        if (stepText.startsWith("Then")) return "Then";
        if (stepText.startsWith("And")) return "And";
        return "And";
    }

    @Override
    public String toString() {
        return keyword + " " + stepText
                + "  [method=" + methodName + "]"
                + "  (" + filePath + ")";
    }
}