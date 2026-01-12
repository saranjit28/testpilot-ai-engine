package com.testpilot.ai.util;

import com.testpilot.ai.model.StepDefinition;

import java.util.*;

public class StepFlattener {

    public static List<StepDefinition> flatten(List<StepDefinition> steps) {

        List<StepDefinition> result = new ArrayList<>();

        for (StepDefinition step : steps) {

            String text = step.getStepText();

            if (!text.contains("|")) {
                result.add(step);
                continue;
            }

            for (String part : text.split("\\|")) {

                String clean = part.trim();
                if (clean.isEmpty()) continue;

                result.add(new StepDefinition(
                        clean,
                        clean,
                        step.getFilePath(),
                        step.getMethodName(),
                        new HashSet<>(step.getKeywords())
                ));
            }
        }

        return result;
    }
}