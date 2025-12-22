package com.testpilot.ai.util;

import java.nio.file.Path;

public class ProjectPathResolver {

    private static final Path ROOT =
            ProjectRootDetector.detect();

    public static Path uiStepsPath() {
        return ROOT.resolve(
                "src/test/java"
        );
    }

    public static Path pageObjectPath() {
        return ROOT.resolve(
                "src/test/java/com/automation/ospi/pageobject"
        );
    }

    public static Path apiSetupPath() {
        return ROOT.resolve(
                "src/main/java/com/automation/ospi/projectsetup"
        );
    }

    public static Path serviceLayerPath() {
        return ROOT.resolve(
                "src/main/java/com/automation/rei/govgrants"
        );
    }
}
