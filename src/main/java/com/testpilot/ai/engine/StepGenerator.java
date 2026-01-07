package com.testpilot.ai.engine;

import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.store.StepStore;

public class StepGenerator {

    private final StepStore stepStore;

    public StepGenerator(StepStore stepStore) {
        this.stepStore = stepStore;
    }

    public void generate(StepDefinition step) {
        stepStore.add(step);
    }
}