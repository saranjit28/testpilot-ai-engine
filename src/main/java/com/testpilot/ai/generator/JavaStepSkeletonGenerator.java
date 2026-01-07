package com.testpilot.ai.generator;

import com.testpilot.ai.engine.KeywordExtractor;
import com.testpilot.ai.engine.TextNormalizer;
import com.testpilot.ai.page.PageInferenceResult;
import com.testpilot.ai.page.PageObjectInferencer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class JavaStepSkeletonGenerator {

    public static String generateClass(
            String packageName,
            List<String> steps
    ) {

        String className =
                "GeneratedSteps_" + timestamp();

        StringBuilder sb = new StringBuilder();

        sb.append("package ").append(packageName).append(";\n\n");
        sb.append("import io.cucumber.java.en.*;\n\n");
        sb.append("public class ").append(className).append(" {\n\n");

        for (String step : steps) {

            // 🔹 STEP 8: PageObject inference
            Set<String> keywords =
                    KeywordExtractor.extract(step);

            PageInferenceResult inference =
                    PageObjectInferencer.infer(keywords);

            String methodName = toMethodName(step);

            sb.append("    @And(\"")
                    .append(step)
                    .append("\")\n");

            sb.append("    public void ")
                    .append(methodName)
                    .append("() {\n");

            sb.append("        ")
                    .append(inference.getPageName())
                    .append(" page = new ")
                    .append(inference.getPageName())
                    .append("();\n");

            sb.append("        page.")
                    .append(inference.getAction())
                    .append(toCamel(step))
                    .append("();\n");

            sb.append("    }\n\n");
        }

        sb.append("}\n");

        return sb.toString();
    }

    public static String generateFileName() {
        return "GeneratedSteps_" + timestamp() + ".java";
    }

    private static String toMethodName(String step) {
        return "step" + toCamel(step);
    }

    private static String toCamel(String text) {

        String normalized =
                TextNormalizer.normalize(text);

        String[] parts = normalized.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String p : parts) {
            sb.append(Character.toUpperCase(p.charAt(0)))
                    .append(p.substring(1));
        }

        return sb.toString();
    }

    private static String timestamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}