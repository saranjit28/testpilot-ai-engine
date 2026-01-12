package com.testpilot.ai.extractor;

import com.testpilot.ai.extractor.KeywordExtractor;
import com.testpilot.ai.model.StepDefinition;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.testpilot.ai.util.StepTextNormalizer;

public class JavaStepDefinitionExtractor {

    private static final Pattern STEP_ANNOTATION =
            Pattern.compile("@(Given|When|Then|And)\\(\"([^\"]+)\"\\)");

    private static final Pattern METHOD_PATTERN =
            Pattern.compile("public\\s+void\\s+(\\w+)\\s*\\(");

    public static List<StepDefinition> extract(Path repoRoot) {

        List<StepDefinition> steps = new ArrayList<>();

        try {
            Files.walk(repoRoot)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(javaFile ->
                            extractFromFile(repoRoot, javaFile, steps)
                    );

        } catch (IOException e) {
            throw new RuntimeException("Failed to scan Java step definitions", e);
        }

        return steps;
    }

    private static void extractFromFile(
            Path repoRoot,
            Path javaFile,
            List<StepDefinition> steps
    ) {

        try {
            List<String> lines = Files.readAllLines(javaFile);

            for (int i = 0; i < lines.size(); i++) {

                String line = lines.get(i).trim();
                Matcher stepMatcher = STEP_ANNOTATION.matcher(line);

                if (!stepMatcher.find()) {
                    continue;
                }

                String stepText = stepMatcher.group(2);

                // find method name below annotation
                for (int j = i + 1; j < lines.size(); j++) {
                    Matcher methodMatcher =
                            METHOD_PATTERN.matcher(lines.get(j));

                    if (methodMatcher.find()) {

                        String methodName = methodMatcher.group(1);
                        String relativePath =
                                repoRoot.relativize(javaFile).toString();

                        steps.add(
                                new StepDefinition(
                                        stepText,
                                        StepTextNormalizer.normalize(stepText),
                                        relativePath,
                                        methodName,
                                        KeywordExtractor.extract(stepText)
                                ));


                        break;
                    }
                }
            }

        } catch (IOException ignored) {
        }
    }
}
