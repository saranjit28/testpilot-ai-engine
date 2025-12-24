package com.testpilot.ai.ai.store;

import com.testpilot.ai.azure.AzureRepoResolver;
import com.testpilot.ai.config.AzureDevOpsConfig;
import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.tfs.AzureWorkspaceResolver;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepStore {

    // 🔥 ONLY VALID STEP ANNOTATIONS
    private static final Pattern STEP_PATTERN =
            Pattern.compile("@(Given|When|Then|And)\\s*\\(\\s*\"([^\"]+)\"\\s*\\)");

    // 🔥 SCAN MULTIPLE STEP LOCATIONS
    private static final List<Path> STEP_DIRECTORIES = List.of(
            AzureWorkspaceResolver.getSrcMainJava("govgrants-ospi-automation")
                    .resolve("com/automation/rei/govgrantsSteps"),Paths.get("src/main/java")
    );

    public static List<StepDefinition> load() {

        List<StepDefinition> steps = new ArrayList<>();

        for (Path dir : STEP_DIRECTORIES) {

            if (!Files.exists(dir)) {
                System.out.println("⚠ Skipping missing path: " + dir);
                continue;
            }

            try {
                Files.walk(dir)
                        .filter(p -> p.toString().endsWith(".java"))
                        .forEach(file -> extractSteps(file, steps));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Steps loaded = " + steps.size());
        System.out.println("✅ Total steps loaded: " + steps.size());
        return steps;
    }

    // ============================
    // 🔥 CORE STEP EXTRACTION LOGIC
    // ============================
    private static void extractSteps(Path javaFile, List<StepDefinition> steps) {

        try {
            List<String> lines = Files.readAllLines(javaFile);

            for (String line : lines) {

                Matcher matcher = STEP_PATTERN.matcher(line.trim());

                if (matcher.find()) {

                    String keyword = matcher.group(1);
                    String rawRegex = matcher.group(2);

                    List<String> expandedSteps =
                            expandRegexStep(rawRegex);

                    for (String text : expandedSteps) {

                        StepDefinition step = new StepDefinition();
                        step.setKeyword(keyword);
                        step.setStepText(text);
                        step.setFileName(javaFile.toString());

                        steps.add(step);

                        System.out.println("➕ Step found → "
                                + keyword + " " +step.getStepText());
                    }

                }
            }

        } catch (Exception e) {
            System.err.println("❌ Failed to parse: " + javaFile);
            e.printStackTrace();
        }
    }

    // 🔥 Normalize regex steps into Gherkin-like text
    private static String normalizeRegexStep(String regex) {
        return regex
                .replaceAll("\\\\\\^", "")
                .replaceAll("\\$", "")
                .replaceAll("\\(\\[\\^\\\\\"\\]\\*\\)", "{string}")
                .replaceAll("\\(\\.\\*\\)", "{string}")
                .replaceAll("\\\\\"", "\"")
                .trim();
    }

    private static List<String> expandRegexStep(String regex) {

        // 1️⃣ Remove anchors
        String cleaned = regex
                .replace("^", "")
                .replace("$", "")
                .trim();

        // 2️⃣ Handle alternation groups: (a|b|c)
        List<String> results = new ArrayList<>();
        results.add(cleaned);

        Pattern altPattern = Pattern.compile("\\(([^()]+\\|[^()]+)\\)");

        boolean expanded;
        do {
            expanded = false;
            List<String> temp = new ArrayList<>();

            for (String step : results) {
                Matcher m = altPattern.matcher(step);

                if (m.find()) {
                    expanded = true;
                    String[] options = m.group(1).split("\\|");

                    for (String opt : options) {
                        temp.add(
                                step.substring(0, m.start()) +
                                        opt +
                                        step.substring(m.end())
                        );
                    }
                } else {
                    temp.add(step);
                }
            }
            results = temp;

        } while (expanded);

        // 3️⃣ Final cleanup
        List<String> finalSteps = new ArrayList<>();
        for (String step : results) {
            finalSteps.add(
                    step.replaceAll("\\s+", " ").trim()
            );
        }

        return finalSteps;
    }


}