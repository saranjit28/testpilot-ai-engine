package com.testpilot.ai.service;

import com.testpilot.ai.util.AiOutputValidator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class StepPersistenceService {

    private static final Path GENERATED_FILE =
            Path.of("src/test/java/generated/GeneratedSteps.java");

    /**
     * 🔥 Delete old generated steps
     */
    public void refreshGeneratedSteps() {
        try {
            Files.deleteIfExists(GENERATED_FILE);
            Files.createDirectories(GENERATED_FILE.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Failed to refresh generated steps", e);
        }
    }

    /**
     * 🔥 Rebuild GeneratedSteps.java from scratch
     */
    public void persistSteps(List<String> gherkinSteps) {

        refreshGeneratedSteps();

        StringBuilder file = new StringBuilder();

        file.append("""
                package generated;

                import io.cucumber.java.en.*;

                public class GeneratedSteps {

                """);

        for (String step : gherkinSteps) {

            if (!AiOutputValidator.isValidGherkinStep(step)) {
                continue;
            }

            file.append(generateStepMethod(step));
        }

        file.append("}\n");

        try {
            Files.writeString(GENERATED_FILE, file.toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write GeneratedSteps.java", e);
        }
    }

    /**
     * 🔥 Safe Java step generator
     */
    private String generateStepMethod(String gherkinStep) {

        String keyword = extractKeyword(gherkinStep);
        String stepText = removeKeyword(gherkinStep);
        String methodName = toMethodName(stepText);

        return """
                
                    @%s("^%s$")
                    public void %s() {
                        // TODO: implement step logic
                    }

                """.formatted(
                keyword,
                escapeForJava(stepText),
                methodName
        );
    }

    private String extractKeyword(String step) {
        if (step.startsWith("Given")) return "Given";
        if (step.startsWith("When")) return "When";
        if (step.startsWith("Then")) return "Then";
        if (step.startsWith("And")) return "And";
        return "Then";
    }

    private String removeKeyword(String step) {
        return step.replaceFirst("^(Given|When|Then|And)\\s+", "");
    }

    private String toMethodName(String step) {
        return step
                .replaceAll("\"[^\"]*\"", "")
                .replaceAll("[^a-zA-Z ]", "")
                .trim()
                .replaceAll("\\s+", "_")
                .toLowerCase();
    }

    /**
     * 🔥 CRITICAL FIX — prevents invalid Java strings
     */
    private String escapeForJava(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "")
                .replace("\r", "");
    }

    public void insertMethodBody(
            String stepFilePath,
            String methodName,
            String javaBody) {

        // Find method by name
        // Replace TODO or empty body
        // Write file back safely
    }


}
