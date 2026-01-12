package com.testpilot.ai.engine;

import com.testpilot.ai.model.StepDefinition;

import java.util.ArrayList;
import java.util.List;

public class StepDecisionEngine {

    private static final double THRESHOLD = 0.40;

    public static DecisionResult decide(
            List<String> manualSteps,
            List<StepDefinition> repoSteps
    ) {
        List<MatchResult> reused = new ArrayList<>();
        List<String> generated = new ArrayList<>();

        for (String manual : manualSteps) {

            MatchResult best =
                    StepMatcher.findBestMatch(manual, repoSteps);

            if (best != null && best.getConfidence() >= 0.40) {
                reused.add(best);
            } else {
                generated.add(manual);
            }
        }
        return new DecisionResult(reused, generated);
    }

}