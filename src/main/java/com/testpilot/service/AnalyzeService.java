package com.testpilot.service;

import com.testpilot.ai.decision.MatchResult;
import com.testpilot.ai.decision.StepDecisionEngine;
import com.testpilot.ai.engine.KeywordExtractor;
import com.testpilot.ai.index.KeywordIndex;
import com.testpilot.ai.index.KeywordIndexBuilder;
import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.model.TestPilotResponse;
import com.testpilot.ai.output.view.GenerateView;
import com.testpilot.ai.output.view.ReuseView;
import com.testpilot.ai.store.StepStore;
import com.testpilot.dto.AnalyzeRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnalyzeService {

    private final StepStore stepStore = new StepStore();

    /**
     * Main entry point for TestPilot analysis.
     * Called by controllers.
     */
    public TestPilotResponse analyzeManualTestCase(AnalyzeRequest request) {

        // 1️⃣ Fetch all existing repo steps (gherkin + java)
        List<StepDefinition> repoSteps = stepStore.getAll();

        // 2️⃣ Build keyword index
        KeywordIndex keywordIndex =
                KeywordIndexBuilder.build(repoSteps);

        List<ReuseView> reusedSteps = new ArrayList<>();
        List<GenerateView> generatedSteps = new ArrayList<>();

        // 3️⃣ Analyze each manual step
        for (String manualStep : request.getSteps()) {

            Set<String> manualKeywords =
                    KeywordExtractor.extract(manualStep);

            MatchResult decision =
                    StepDecisionEngine.decide(
                            manualStep,
                            manualKeywords,
                            keywordIndex
                    );

            if (decision.isReusable()) {

                StepDefinition step =
                        decision.getMatchedStep();

                reusedSteps.add(
                        new ReuseView(
                                step.getStepText(),
                                toPercent(decision.getConfidence()),
                                step.getFilePath(),
                                step.getMethodName()
                        )
                );

            } else {

                generatedSteps.add(
                        new GenerateView(
                                manualStep,
                                toPercent(decision.getConfidence())
                        )
                );
            }
        }

        // 4️⃣ FINAL RESPONSE (MANDATORY RETURN)
        return TestPilotResponse.build(
                request.getRequestId(),
                request.getTestName(),
                "COMPLETED",
                reusedSteps,
                generatedSteps,
                List.of("Analysis completed successfully")
        );
    }

    /**
     * Converts confidence score to percentage string.
     */
    private String toPercent(double confidence) {
        return Math.round(confidence * 100) + "%";
    }
}