package com.testpilot.parser;

import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.extractor.KeywordExtractor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepDefinitionParser {

    /**
     * Matches:
     *  @Given("I do something")
     *  @When("^I login to \"([^\"]*)\" app$")
     *  @Then("^I (see|do not see) \"([^\"]*)\"$")
     */
    private static final Pattern STEP_PATTERN =
            Pattern.compile(
                    "@(Given|When|Then|And)\\(\"(.*?)\"\\)",
                    Pattern.DOTALL
            );

    public static List<StepDefinition> parse(
            String javaSource,
            String filePath
    ) {

        List<StepDefinition> steps = new ArrayList<>();

        Matcher matcher = STEP_PATTERN.matcher(javaSource);

        while (matcher.find()) {

            String keyword = matcher.group(1);
            String rawExpression = matcher.group(2);

            // 1️⃣ Normalize regex → cucumber style
            String normalized = normalize(rawExpression);

            // 2️⃣ Expand (a|b) patterns
            List<String> expanded = expandAlternatives(normalized);

            // 3️⃣ Extract method name
            String methodName =
                    extractMethodName(javaSource, matcher.end());

            for (String stepText : expanded) {

                Set<String> keywords =
                        KeywordExtractor.extract(stepText);

                steps.add(
                        new StepDefinition(
                                keyword,
                                stepText,
                                stepText.toLowerCase(),
                                methodName,
                                filePath,
                                keywords
                        )
                );
            }
        }

        return steps;
    }

    // ---------------- HELPERS ----------------

    private static String normalize(String expr) {

        // Remove regex anchors
        expr = expr.replaceAll("^\\^", "")
                .replaceAll("\\$$", "");

        // Replace regex string captures with {string}
        expr = expr.replaceAll("\\\\\\\"\\(\\[\\^\\\\\\\"\\]\\*\\)\\\\\\\"", "{string}");
        expr = expr.replaceAll("\\(\\[\\^\\\\\\\"\\]\\*\\)", "{string}");
        expr = expr.replaceAll("\\(\\.\\*\\)", "{string}");

        // Unescape quotes
        expr = expr.replace("\\\"", "");

        return expr.trim();
    }

    private static List<String> expandAlternatives(String expr) {

        // Example: I (see|do not see) X
        Pattern altPattern = Pattern.compile("\\(([^()]+\\|[^()]+)\\)");
        Matcher matcher = altPattern.matcher(expr);

        if (!matcher.find()) {
            return List.of(expr);
        }

        String[] options = matcher.group(1).split("\\|");

        List<String> results = new ArrayList<>();

        for (String option : options) {
            results.add(
                    expr.replace(
                            "(" + matcher.group(1) + ")",
                            option.trim()
                    )
            );
        }

        return results;
    }

    private static String extractMethodName(
            String source,
            int fromIndex
    ) {

        Pattern methodPattern =
                Pattern.compile("public\\s+void\\s+(\\w+)\\s*\\(");

        Matcher matcher =
                methodPattern.matcher(source.substring(fromIndex));

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "UNKNOWN_METHOD";
    }
}