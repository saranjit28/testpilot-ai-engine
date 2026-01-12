package com.testpilot.ai.engine;

import com.testpilot.ai.generator.FeatureFileGenerator;
import com.testpilot.ai.generator.JavaStepSkeletonGenerator;
import com.testpilot.ai.page.PageObjectClassGenerator;
import com.testpilot.ai.page.PageObjectMethodGenerator;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class StepGenerator {

    /**
     * Generates automation artifacts for a manual step
     * when confidence < threshold.
     */
    public void generate(String manualStep, String testName) {

        try {
            // 1️⃣ FEATURE FILE
            String featureContent =
                    FeatureFileGenerator.generateFeature(
                            testName,
                            List.of(manualStep)
                    );

            Path featureDir =
                    Path.of("src/test/generated/features");

            Files.createDirectories(featureDir);

            Files.writeString(
                    featureDir.resolve(
                            testName.replaceAll("\\s+", "") + ".feature"
                    ),
                    featureContent
            );

            // 2️⃣ JAVA STEP DEFINITIONS
            String javaContent =
                    JavaStepSkeletonGenerator.generateClass(
                            "generated.steps",
                            List.of(manualStep)
                    );

            Path stepDir =
                    Path.of("src/test/generated/steps");

            Files.createDirectories(stepDir);

            Files.writeString(
                    stepDir.resolve("GeneratedSteps.java"),
                    javaContent
            );

            // 3️⃣ PAGE OBJECT (DETERMINISTIC FALLBACK)
            String pageName = "GeneratedPage";

            String methodName =
                    PageObjectMethodGenerator
                            .extractMethodName(
                                    manualStep,
                                    pageName
                            );

            String methodBlock =
                    PageObjectMethodGenerator
                            .generateMethod(
                                    manualStep,
                                    pageName
                            );

            Path pageFile =
                    Path.of(
                            "src/test/generated/pages",
                            pageName + ".java"
                    );

            PageObjectClassGenerator.generateOrAppend(
                    pageFile,
                    "generated.pages",
                    pageName,
                    methodBlock,
                    methodName
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to generate automation for step: " + manualStep,
                    e
            );
        }
    }
}
