package com.testpilot.ai.store;

import com.testpilot.ai.model.StepDefinition;
import com.testpilot.azure.StepsFileLister;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RemoteStepStore {

    private List<StepDefinition> cachedSteps;

    public List<StepDefinition> loadSteps() {

        if (cachedSteps != null) {
            return cachedSteps;
        }

        try {
            System.out.println("🔄 Loading automation steps from TFS...");
            cachedSteps = StepsFileLister.fetchAllSteps();
            System.out.println("✅ Loaded steps from TFS = " + cachedSteps.size());
        } catch (Exception e) {
            System.err.println("⚠️ TFS fetch failed. Running in SAFE MODE.");
            e.printStackTrace();
            cachedSteps = Collections.emptyList();
        }

        return cachedSteps;
    }
}
