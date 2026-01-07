package com.testpilot.parser;

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
        }

        return steps;
    }

    private static String toMatchText(String regex) {

        if (regex == null) return "";

        return regex
                .replace("^", "")
                .replace("$", "")
                .replaceAll("\\\\\"", "\"")
                .replaceAll("\"([^\"]*)\"", "{string}")
                .replaceAll("\\(\\[\\^\"\\]\\*\\)", "{string}")
                .replaceAll("\\d+", "{string}")
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase();
    }
}