package com.testpilot.ai.engine;

import java.util.List;

public class DecisionResult {

    private final List<MatchResult> reused;
    private final List<String> generated;

    public DecisionResult(
            List<MatchResult> reused,
            List<String> generated
    ) {
        this.reused = reused;
        this.generated = generated;
    }

    public List<MatchResult> getReused() {
        return reused;
    }

    public List<String> getGenerated() {
        return generated;
    }
}