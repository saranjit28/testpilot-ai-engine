package com.testpilot.ai.tfs;

import java.nio.file.Files;
import java.nio.file.Path;

public final class AzureWorkspaceResolver {

    private AzureWorkspaceResolver() {}

    public static Path getRepoRoot(String repoName) {

        // ------------------------------------------------
        // 1️⃣ Azure DevOps pipeline (preferred)
        // ------------------------------------------------
        String workspace = System.getenv("BUILD_SOURCESDIRECTORY");

        if (workspace != null && !workspace.isBlank()) {
            Path repo = Path.of(workspace).resolve(repoName);
            if (Files.exists(repo)) {
                return repo;
            }
        }

        // ------------------------------------------------
        // 2️⃣ Local developer machine (IntelliJ / CLI)
        // ------------------------------------------------
        Path current = Path.of("").toAbsolutePath();

        while (current != null) {

            // Case 1: running from parent folder
            Path candidate = current.resolve(repoName);
            if (Files.exists(candidate.resolve(".git"))
                    || Files.exists(candidate.resolve("pom.xml"))) {
                return candidate;
            }

            // Case 2: already inside repo
            if (Files.exists(current.resolve(".git"))
                    || Files.exists(current.resolve("pom.xml"))) {
                return current;
            }

            current = current.getParent();
        }

        // ------------------------------------------------
        // 3️⃣ Hard fail (correct behavior)
        // ------------------------------------------------
        throw new IllegalStateException(
                "Unable to locate repo '" + repoName + "'.\n" +
                        "Fix by either:\n" +
                        " - Running inside Azure DevOps pipeline, or\n" +
                        " - Opening IntelliJ from the folder that contains the repo");
    }

    public static Path getSrcMainJava(String repoName) {
        return getRepoRoot(repoName).resolve("src/main/java");
    }

    public static Path getSrcTestJava(String repoName) {
        return getRepoRoot(repoName).resolve("src/test/java");
    }
}