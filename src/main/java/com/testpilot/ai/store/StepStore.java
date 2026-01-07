package com.testpilot.ai.store;

import com.testpilot.ai.model.StepDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StepStore {

    private final List<StepDefinition> steps = new ArrayList<>();

    public void add(StepDefinition step) {
        steps.add(step);
    }

    public List<StepDefinition> getAll() {
        return Collections.unmodifiableList(steps);
    }

    public boolean isEmpty() {
        return steps.isEmpty();
    }

    public int size() {
        return steps.size();
    }
}
