package com.testpilot.ai.store;

import com.testpilot.ai.model.StepDefinition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StepStore {

    private final RemoteStepStore remoteStepStore;

    public StepStore(RemoteStepStore remoteStepStore) {
        this.remoteStepStore = remoteStepStore;
    }

    public List<StepDefinition> getAllSteps() {
        return remoteStepStore.loadSteps();
    }
}
