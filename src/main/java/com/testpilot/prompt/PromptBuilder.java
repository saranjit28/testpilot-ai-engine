package com.testpilot.prompt;

import java.util.List;

public class PromptBuilder {

    public static String buildMissingStepPrompt(
            String manualStep,
            List<String> existingSteps
    ) {

        return """

Manual Test Step:
%s

Existing Gherkin Steps:
%s

Rules:
- If step exists → reuse
- If not → create NEW Gherkin step
- Provide Java step definition
- Use PageObject pattern
- Do NOT include explanations
- Return ONLY code

Output format:
Gherkin:
<gherkin>

Java:
<java code>
""".formatted(
                manualStep,
                String.join("\n", existingSteps)
        );
    }
}