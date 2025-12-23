package com.testpilot.ai.engine;
import com.testpilot.ai.ai.OllamaClient;

import java.util.List;

public class AiReasoningService {
    public String generateMissingStep(String manualStep, List<String> existingSteps) {

        String prompt = """
    You are a test automation expert.

    RULES:
    - Output ONLY ONE valid Gherkin step.
    - The step MUST start with: Given, When, Then, or And.
    - Do NOT explain anything.
    - Do NOT return errors.
    - Do NOT include quotes outside the step.
    - Do NOT return Java code.

    Manual step:
    %s

    Return ONLY the corrected Gherkin step:
    """.formatted(manualStep);

        return OllamaClient.generate(prompt).trim();
    }

}
