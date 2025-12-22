package com.testpilot.ai.parser;

import com.testpilot.ai.model.StepDefinition;

import java.util.*;
import java.util.regex.*;

public class StepDefinitionParser {

    private static final Pattern STEP_PATTERN =
            Pattern.compile(
                    "@(Given|When|Then|And)\\s*\\(\\s*\"([^\"]+)\"\\s*\\)"
            );

    public static List<StepDefinition> parse(
            String javaSource,
            String fileName
    ) {

        List<StepDefinition> steps = new ArrayList<>();
        Matcher matcher = STEP_PATTERN.matcher(javaSource);

        while (matcher.find()) {

            String keyword = matcher.group(1);
            String regex = matcher.group(2);

            if (regex.contains("|")) {
                extractOrSteps(keyword, regex, fileName, steps);
            } else {
                steps.add(build(keyword, normalize(regex), fileName));
            }
        }

        return steps;
    }

    // Handles: (softly see|softly do not see)
    private static void extractOrSteps(
            String keyword,
            String regex,
            String fileName,
            List<StepDefinition> steps
    ) {

        Pattern orPattern = Pattern.compile("\\(([^)]+)\\)");
        Matcher matcher = orPattern.matcher(regex);

        if (!matcher.find()) return;

        String[] options = matcher.group(1).split("\\|");

        for (String option : options) {
            String stepText =
                    regex.replace(matcher.group(0), option);
            steps.add(build(keyword, normalize(stepText), fileName));
        }
    }

    private static String normalize(String regex) {
        return regex
                .replace("^", "")
                .replace("$", "")
                .replaceAll("\\(\\[\\^\"\\]\\*\\)", "{string}")
                .replaceAll("\\\\\"", "\"")
                .trim();
    }

    private static StepDefinition build(
            String keyword,
            String text,
            String fileName
    ) {
        StepDefinition step = new StepDefinition();
        step.setKeyword(keyword);
        step.setStepText(text);
        step.setFileName(fileName);
        return step;
    }
}