package com.testpilot.ai.service;

import com.testpilot.ai.ai.OllamaClient;
import com.testpilot.ai.prompt.PromptBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiReasoningService {

    private final OllamaClient ollamaClient;

    public AiReasoningService(OllamaClient ollamaClient) {
        this.ollamaClient = ollamaClient;
    }

    public String generateMissingStep(
            String manualStep,
            List<String> repoSteps
    ) {
        String prompt =
                PromptBuilder.buildMissingStepPrompt(
                        manualStep,
                        repoSteps
                );

        return ollamaClient.generate(prompt);
    }

    public String generate(String prompt) {
        return prompt;
    }
}
