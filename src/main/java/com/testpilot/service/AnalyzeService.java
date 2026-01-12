package com.testpilot.service;

import com.testpilot.ai.engine.DecisionResult;
import com.testpilot.ai.engine.MatchResult;
import com.testpilot.ai.engine.StepDecisionEngine;
import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.output.view.GenerateView;
import com.testpilot.ai.output.view.ReuseView;
import com.testpilot.ai.store.StepStore;
import com.testpilot.ai.model.TestPilotResponse;
import com.testpilot.dto.AnalyzeRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyzeService {

    private final StepStore stepStore;

    public AnalyzeService(StepStore stepStore) {
        this.stepStore = stepStore;
    }

    public TestPilotResponse analyzeManualTestCase(AnalyzeRequest request) {

        List<StepDefinition> repoSteps = stepStore.getAllSteps();

        System.out.println(">>> REPO STEPS COUNT = " + repoSteps.size());

        DecisionResult decision =
                StepDecisionEngine.decide(
                        request.getSteps(),
                        repoSteps
                );

        List<ReuseView> reusedViews =
                decision.getReused().stream()
                        .map(r -> {
                            StepDefinition s = r.getStep();
                            return new ReuseView(
                                    s.getKeyword(),
                                    s.getStepText(),
                                    s.getMethodName(),
                                    s.getFilePath(),
                                    String.valueOf(Math.round(r.getConfidence() * 100))
                            );
                        })
                        .toList();

        List<GenerateView> generatedViews =
                decision.getGenerated().stream()
                        .map(s -> new GenerateView(s, "PENDING"))
                        .toList();

        return TestPilotResponse.build(
                "COMPLETED",
                "Analysis completed successfully",
                request.getTestName(),
                reusedViews,
                generatedViews,
                List.of()
        );
    }

    private ReuseView toReuseView(MatchResult result) {

        StepDefinition step = result.getStep();

        return new ReuseView(
                step.getKeyword(),                 // Given / When / Then / And
                step.getStepText(),                // Step text
                step.getMethodName(),              // Java method
                step.getFilePath(),                // Repo path
                result.getConfidence() + "%"        // Confidence
        );
    }

    private GenerateView toGenerateView(String manualStep) {
            return new GenerateView(
                    manualStep,
                    "PENDING"
            );
        }

    }