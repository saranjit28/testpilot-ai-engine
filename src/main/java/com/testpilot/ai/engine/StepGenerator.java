package com.testpilot.ai.engine;

import com.testpilot.ai.ai.store.StepStore;
import com.testpilot.ai.model.StepDefinition;

import java.util.List;

public class StepGenerator {

    public static void generate(String gherkinStep) {

        List<StepDefinition> repoSteps =
                StepStore.load();

        System.out.println("\n🤖 CLAUDE GENERATED JAVA STEP:");
        System.out.println("--------------------------------");
        System.out.println("--------------------------------");
    }
}