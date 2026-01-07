package com.testpilot.ai.index;

import com.testpilot.ai.model.StepDefinition;

import java.util.*;

public class KeywordIndex {

    private final Map<String, List<StepDefinition>> index = new HashMap<>();

    public void add(StepDefinition step) {

        for (String keyword : step.getKeywords()) {
            index
                    .computeIfAbsent(keyword, k -> new ArrayList<>())
                    .add(step);
        }
    }

    public List<StepDefinition> lookup(Set<String> keywords) {

        Set<StepDefinition> candidates = new HashSet<>();

        for (String keyword : keywords) {
            List<StepDefinition> steps = index.get(keyword);
            if (steps != null) {
                candidates.addAll(steps);
            }
        }

        return new ArrayList<>(candidates);
    }

    public boolean isEmpty() {
        return index.isEmpty();
    }
}