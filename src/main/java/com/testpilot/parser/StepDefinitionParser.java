package com.testpilot.parser;

import com.testpilot.ai.model.StepDefinition;
import com.testpilot.ai.extractor.KeywordExtractor;
import com.testpilot.ai.util.StepTextNormalizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StepDefinitionParser {

    private static final Pattern STEP_PATTERN =
            Pattern.compile("@(Given|When|Then|And|But)\\(\"([^\"]+)\"\\)");

    public static List<StepDefinition> parse(String fileContent, String filePath) {

        List<StepDefinition> steps = new ArrayList<>();
        Matcher matcher = STEP_PATTERN.matcher(fileContent);

        while (matcher.find()) {

            String keyword = matcher.group(1);
            String rawAnnotation = matcher.group(2);

            String cleanStep =
                    rawAnnotation
                            .replaceAll("\\^|\\$", "")
                            .replaceAll("\"[^\"]*\"", "{string}");

            String matchText =
                    StepTextNormalizer.normalize(cleanStep);

            steps.add(new StepDefinition(
                    keyword,
                    cleanStep,
                    matchText,
                    extractMethodName(fileContent, matcher.end()),
                    filePath,
                    KeywordExtractor.extract(matchText)
            ));
        }

        return steps;
    }

    private static String extractMethodName(String content, int index) {
        Pattern methodPattern =
                Pattern.compile("void\\s+(\\w+)\\s*\\(");
        Matcher m =
                methodPattern.matcher(content.substring(index));
        return m.find() ? m.group(1) : "unknownMethod";
    }
}