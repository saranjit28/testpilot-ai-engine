package com.testpilot.ai.service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StepImplementationAgent {

    private final AiReasoningService aiReasoningService;

    public StepImplementationAgent(AiReasoningService aiReasoningService) {
        this.aiReasoningService = aiReasoningService;
    }

    public String generateMethodBody(
            String stepText,
            List<String> pageObjectMethods) {

        String prompt = """
        You are a Java Selenium automation expert.

        Implement the following Cucumber step using existing PageObject methods.

        Step:
        %s

        Available PageObject methods:
        %s

        Rules:
        - Use PageObject methods only
        - Follow POM design
        - Do NOT create locators
        - Return ONLY Java code inside method body
        """.formatted(stepText, String.join("\n", pageObjectMethods));

        return aiReasoningService.generate(prompt);
    }
}
