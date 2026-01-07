package com.testpilot.ai;

import com.testpilot.ai.model.StepDefinition;

import java.util.List;

public class StepPromptBuilder {

    public static String buildPrompt(List<StepDefinition> steps) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                Below are existing Cucumber step definitions from a real framework.
                Use them to avoid duplicates and suggest correct steps.

                ================== STEP DEFINITIONS ==================
                """);

        for (StepDefinition step : steps) {
            prompt.append(step.getKeywords())
                    .append(" ")
                    .append(step.getStepText())
                    .append("\n")
                    .append("(File: ")
                    .append(step.getFileName())
                    .append(")\n\n");
        }

        prompt.append("""
                ======================================================
                Instructions:
                - Reuse existing steps where possible
                - Generate missing steps only if needed
                - Follow the same wording and patterns
                """);

        return prompt.toString();
    }
}

