package com.testpilot.ai.export;

import com.testpilot.ai.model.StepDefinition;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class DummyStepsWriter {

    public static void write(
            List<StepDefinition> steps,
            Path outputFile
    ) throws Exception {

        Map<String, List<StepDefinition>> byFile =
                steps.stream()
                        .collect(Collectors.groupingBy(
                                StepDefinition::getFilePath,
                                TreeMap::new,
                                Collectors.toList()
                        ));

        List<String> lines = new ArrayList<>();

        for (Map.Entry<String, List<StepDefinition>> entry : byFile.entrySet()) {

            lines.add("========================================");
            lines.add("FILE: " + entry.getKey());
            lines.add("========================================");

            for (StepDefinition step : entry.getValue()) {
                lines.add(
                        step.getKeyword() + " "
                                + step.getStepText()
                                + "  [method=" + step.getMethodName() + "]"
                );
            }

            lines.add(""); // blank line
        }

        Files.createDirectories(outputFile.getParent());
        Files.write(outputFile, lines);
    }
}
