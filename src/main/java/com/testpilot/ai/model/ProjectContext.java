package com.testpilot.ai.model;

import java.nio.file.Path;

public class ProjectContext {

    private Path repoRoot;

    public ProjectContext(Path repoRoot) {
        this.repoRoot = repoRoot;
    }

    public Path testSource() {
        return repoRoot.resolve("src/test/java");
    }

    public Path mainSource() {
        return repoRoot.resolve("src/main/java");
    }

    public static Path testSteps(ProjectContext ctx) {
        return ctx.testSource();
    }

    public static Path services(ProjectContext ctx) {
        return ctx.mainSource();
    }

}
