package com.testpilot.runner;

import com.testpilot.ai.store.StepStore;

public class AiSuggestionRunner {

    private final StepStore stepStore;

    public AiSuggestionRunner(StepStore stepStore) {
        this.stepStore = stepStore;
    }

    public void run() {
        // use stepStore.getAll()
    }
}
