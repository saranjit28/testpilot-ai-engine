package com.testpilot.ai.index;

import com.testpilot.ai.model.StepDefinition;

import java.util.List;

public class KeywordIndexBuilder {

    public static KeywordIndex build(List<StepDefinition> steps) {

        KeywordIndex index = new KeywordIndex();

        for (StepDefinition step : steps) {
            index.add(step);
        }

        return index;
    }
}