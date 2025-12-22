package com.testpilot.ai.engine;

import com.testpilot.ai.ai.free.FreeStepSuggestionEngine;
import com.testpilot.ai.ai.store.StepStore;
import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.model.TestPilotResponse;

import java.util.ArrayList;
import java.util.List;

public class TestPilotAIEngine {

    public static TestPilotResponse run(String manualText) {

        // TEMP Gherkin generator
        List<String> gherkinSteps = List.of(
                "Then I softly see the text \"Welcome\" at index \"1\""
        );

        List<StepDefinition> repoSteps = StepStore.load();

        List<StepMatcher.StepMatch> matchedSteps = new ArrayList<>();
        List<String> missingSteps = new ArrayList<>();

        for (String gStep : gherkinSteps) {

            List<StepMatcher.StepMatch> matches =
                    StepMatcher.findMatches(gStep, repoSteps);

            if (matches.isEmpty()) {
                String suggestion =
                        FreeStepSuggestionEngine.suggestStep(gStep);
                missingSteps.add(suggestion);
            } else {
                matchedSteps.addAll(matches);
            }

        }

        return TestPilotResponse.build(
                gherkinSteps,
                matchedSteps,
                missingSteps
        );
    }
}
