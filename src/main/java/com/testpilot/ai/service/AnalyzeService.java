package com.testpilot.ai.service;

import com.testpilot.ai.dto.AnalyzeRequest;
import com.testpilot.ai.engine.StepMatcher;
import com.testpilot.ai.ai.store.StepStore;
import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.model.TestPilotResponse;
import com.testpilot.ai.util.AiOutputValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AnalyzeService {

    private static final double MATCH_THRESHOLD = 0.40;

    private final AiReasoningService aiReasoningService;
    private final StepPersistenceService stepPersistenceService;

    public AnalyzeService(AiReasoningService aiReasoningService,
                          StepPersistenceService stepPersistenceService) {
        this.aiReasoningService = aiReasoningService;
        this.stepPersistenceService = stepPersistenceService;
    }

    public TestPilotResponse analyzeManualTestCase(AnalyzeRequest request) {

        // 🔥 Reset generated steps every run
        stepPersistenceService.refreshGeneratedSteps();

        String manualStep = request.getManualTestCase();
        List<String> gherkinSteps = List.of(manualStep);

        List<StepMatcher.StepMatch> matchedSteps = new ArrayList<>();
        List<String> missingSteps = new ArrayList<>();

        // 1️⃣ Load repo steps
        List<StepDefinition> repoSteps = StepStore.load();

        // 2️⃣ Match against existing steps
        List<StepMatcher.StepMatch> matches =
                StepMatcher.findMatches(manualStep, repoSteps);

        Optional<StepMatcher.StepMatch> bestMatch =
                matches.stream()
                        .max(Comparator.comparingDouble(StepMatcher.StepMatch::getConfidence));

        // 3️⃣ Decision based on confidence
        if (bestMatch.isPresent()
                && bestMatch.get().getConfidence() >= MATCH_THRESHOLD) {

            // ✅ MATCH FOUND — do NOT generate new step
            matchedSteps.add(bestMatch.get());

        } else {

            // ❌ NO GOOD MATCH — generate via AI
            String aiStep =
                    aiReasoningService.generateMissingStep(
                            manualStep,
                            repoSteps.stream()
                                    .map(StepDefinition::getStepText)
                                    .toList()
                    );

            if (AiOutputValidator.isValidGherkinStep(aiStep)) {
                missingSteps.add(aiStep);
                stepPersistenceService.persistSteps(List.of(aiStep));
            } else {
                missingSteps.add("⚠ AI suggestion rejected (invalid format)");
            }
        }

        // 4️⃣ Build response
        return TestPilotResponse.build(
                "ANALYZED",
                "Manual test case analyzed successfully",
                manualStep,
                gherkinSteps,
                matchedSteps,
                missingSteps
        );
    }
}
