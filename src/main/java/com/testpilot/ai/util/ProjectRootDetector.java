package com.testpilot.ai.util;

import java.nio.file.Path;

public class ProjectRootDetector {

    public static Path detect() {

        Path current = Path.of("").toAbsolutePath();

        while (current != null) {

            if (current.resolve("pom.xml").toFile().exists()
                    || current.resolve(".git").toFile().exists()) {
                return current;
            }

            current = current.getParent();
        }

        throw new RuntimeException("Project root not found");
    }
}
