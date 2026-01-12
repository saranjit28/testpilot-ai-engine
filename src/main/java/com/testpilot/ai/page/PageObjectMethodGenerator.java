package com.testpilot.ai.page;
import com.testpilot.ai.util.StepTextNormalizer;

public class PageObjectMethodGenerator {

    public static String generateMethod(
            String stepText,
            PageInferenceResult inference
    ) {

        String methodName =
                inference.getAction()
                        + toCamel(stepText);

        String prompt =
                com.testpilot.ai.llm.SeleniumPromptBuilder.build(
                        stepText,
                        inference.getPageName(),
                        methodName
                );


        String body = com.testpilot.ai.llm.OllamaClient.generate(prompt);

        if (body == null || body.isBlank()) {
            body = "throw new RuntimeException(\"Implementation required\");";
        }


        StringBuilder sb = new StringBuilder();

        sb.append("    public void ")
                .append(methodName)
                .append("() {\n");

        for (String line : body.split("\n")) {
            sb.append("        ").append(line).append("\n");
        }

        sb.append("    }\n");

        return sb.toString();
    }

    private static String toCamel(String text) {

        String normalized = StepTextNormalizer.normalize(text);


        String[] parts = normalized.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String p : parts) {
            sb.append(Character.toUpperCase(p.charAt(0)))
                    .append(p.substring(1));
        }

        return sb.toString();
    }

    public static String extractMethodName(
            String stepText,
            String pageName
    ) {
        return "handle" + stepText
                .replaceAll("[^a-zA-Z]", " ")
                .trim()
                .replaceAll("\\s+", "")
                .replaceFirst("^.",
                        String.valueOf(
                                Character.toUpperCase(
                                        stepText.charAt(0)
                                )
                        )
                );
    }

    public static String generateMethod(
            String stepText,
            String pageName
    ) {
        return """
            public void %s() {
                throw new RuntimeException("Implementation required");
            }
            """.formatted(
                extractMethodName(stepText, pageName)
        );
    }


    public static String extractMethodName(
            String stepText,
            PageInferenceResult inference
    ) {
        return inference.getAction()
                + toCamel(stepText);
    }

}