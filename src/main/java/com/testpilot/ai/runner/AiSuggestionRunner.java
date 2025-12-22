package com.testpilot.ai.runner;

import com.testpilot.ai.ai.store.StepStore;
import com.testpilot.ai.engine.StepMatcher;

import java.util.List;

public class AiSuggestionRunner {

    public static void main(String[] args) {

        String userStep =
                "Then I softly see the text \"Welcome\" at index \"1\"";

        System.out.println("🔍 INPUT STEP:");
        System.out.println(userStep);

        var repoSteps = StepStore.load();

        List<StepMatcher.StepMatch> matches =
                StepMatcher.findMatches(userStep, repoSteps);

        if (matches.isEmpty()) {

            System.out.println("\n❌ No match ≥ 70%");
            System.out.println("AI must generate new step.");

        } else {

            StepMatcher.StepMatch best = matches.get(0);

            System.out.println("\n✅ BEST MATCH FOUND");
            System.out.println("Confidence : "
                    + Math.round(best.getConfidence() * 100) + "%");

            System.out.println("\nSuggested Reuse:");
            System.out.println(
                    best.getStep().getKeyword() + " " +
                            best.getStep().getStepText()
            );

            System.out.println("\nLocation:");
            System.out.println(best.getStep().getFileName());
        }
    }
}
