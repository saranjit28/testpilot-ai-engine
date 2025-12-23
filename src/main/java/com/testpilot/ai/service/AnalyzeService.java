package com.testpilot.ai.service;

import com.testpilot.ai.dto.AnalyzeRequest;
import com.testpilot.ai.model.TestPilotResponse;
import com.testpilot.ai.util.AiOutputValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AnalyzeService {

    private final AiReasoningService aiReasoningService;
    private final StepPersistenceService stepPersistenceService;

    public AnalyzeService(AiReasoningService aiReasoningService,
                          StepPersistenceService stepPersistenceService) {
        this.aiReasoningService = aiReasoningService;
        this.stepPersistenceService = stepPersistenceService;
    }

    public TestPilotResponse analyzeManualTestCase(AnalyzeRequest request) {

        // -----------------------------------------------------------------
        // 1️⃣  Clean the generated‑steps folder (once)
        // -----------------------------------------------------------------
        stepPersistenceService.refreshGeneratedSteps();

        // -----------------------------------------------------------------
        // 2️⃣  Extract the raw manual step (the API currently sends one line)
        // -----------------------------------------------------------------
        String manualStep = request.getManualTestCase();
        List<String> gherkinSteps = Collections.singletonList(manualStep);

        // -----------------------------------------------------------------
        // 3️⃣  Prepare containers for the response
        // -----------------------------------------------------------------
        List<String> missingSteps = new ArrayList<>(); // valid AI‑generated steps
        List<String> errorMessages = new ArrayList<>(); // strings prefixed with ⚠

        // -----------------------------------------------------------------
        // 4️⃣  If the input already looks like a Gherkin step, we can skip AI.
        // -----------------------------------------------------------------
        boolean alreadyGherkin = AiOutputValidator.isValidGherkinStep(manualStep);
        String aiStep;
        if (alreadyGherkin) {
            // Treat the incoming line as a “missing” step that already exists.
            aiStep = manualStep;
        } else {
            // -----------------------------------------
            // 5️⃣  Ask the AI for a suggestion once
            // -----------------------------------------
            aiStep = aiReasoningService.generateMissingStep(manualStep, List.of());
        }

        // -----------------------------------------------------------------
        // 6️⃣  Validate the AI (or bypass) result **once**
        // -----------------------------------------------------------------
        boolean isValid = AiOutputValidator.isValidGherkinStep(aiStep);

        if (isValid) {
            missingSteps.add(aiStep);                     // ← only ONE entry
            // Persist the newly generated step (if it does not already exist)
            stepPersistenceService.persistSteps(List.of(aiStep));
        } else {
            errorMessages.add("⚠ AI failed to generate valid step for: " + manualStep);
        }

        // -----------------------------------------------------------------
        // 7️⃣  Build the response – note we keep both collections
        // -----------------------------------------------------------------
        return TestPilotResponse.build(
                "ANALYZED",
                "Manual test case analyzed successfully",
                manualStep,
                gherkinSteps,
                List.of(),
                missingSteps,
                errorMessages
        );


    }
}
