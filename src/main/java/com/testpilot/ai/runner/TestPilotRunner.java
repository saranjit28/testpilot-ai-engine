package com.testpilot.ai.runner;

import com.testpilot.ai.engine.TestPilotAIEngine;
import com.testpilot.ai.engine.StepMatcher;
import com.testpilot.ai.model.TestPilotResponse;

public class TestPilotRunner {

    public static void main(String[] args) {

        TestPilotResponse response =
                TestPilotAIEngine.run(
                        "User sees welcome message"
                );

        System.out.println("========== GHERKIN ==========");
        response.getGherkinSteps()
                .forEach(System.out::println);

        System.out.println("\n========== MATCHED ==========");
        for (StepMatcher.StepMatch match : response.getMatchedSteps()) {
            System.out.println(
                    match.getStep().getKeyword() + " " +
                            match.getStep().getStepText() +
                            "  [confidence=" +
                            Math.round(match.getConfidence() * 100) + "%]"
            );
            System.out.println("Location: "
                    + match.getStep().getFileName());
        }

        System.out.println("\n========== MISSING (AI NEEDED) ==========");
        response.getMissingSteps()
                .forEach(System.out::println);
    }
}
