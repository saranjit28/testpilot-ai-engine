package com.testpilot.runner;

import com.testpilot.ai.engine.MatchResult;
import com.testpilot.ai.engine.StepMatcher;
import com.testpilot.ai.model.StepDefinition;
import com.testpilot.azure.StepsFileLister;

import java.util.List;

public class StepMatcherRunner {

    public static void main(String[] args) throws Exception {

        List<StepDefinition> repoSteps =
                StepsFileLister.fetchAllSteps();

        String manualStep =
                "I click on icons for column values inside table";

        StepMatcher matcher = new StepMatcher();
        MatchResult result =
                StepMatcher.findBestMatch(manualStep, repoSteps);

        result.getStep();


        System.out.println("Manual Step: " + manualStep);
        System.out.println("Matched Step: "
                + result.getStep());
        System.out.println("Confidence: "
                + result.getConfidence() + "%");
    }
}
