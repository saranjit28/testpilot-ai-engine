package com.testpilot.ai.output;

import com.testpilot.ai.decision.MatchResult;
import com.testpilot.ai.model.StepDefinition;

public class DecisionOutputMapper {

    public static Object map(
            String manualStep,
            MatchResult result
    ) {

        if (result.isReusable()) {

            StepDefinition step = result.getMatchedStep();

            return new ReuseResult(
                    step.getStepText(),
                    result.getConfidence(),
                    step.getFilePath(),
                    step.getMethodName()
            );
        }

        return new GenerateRequest(
                manualStep,
                result.getConfidence()
        );
    }
}