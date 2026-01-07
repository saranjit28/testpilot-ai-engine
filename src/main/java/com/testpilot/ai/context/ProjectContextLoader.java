package com.testpilot.ai.context;

import com.testpilot.ai.util.ProjectPathResolver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class ProjectContextLoader {

    public static String loadUIContext() {
        return read(ProjectPathResolver.pageObjectPath());
    }

    public static String loadAPIContext() {
        return read(ProjectPathResolver.apiSetupPath());
    }

    public static String loadServiceContext() {
        return read(ProjectPathResolver.serviceLayerPath());
    }

    private static String read(Path root) {

        try {
            return Files.walk(root)
                    .filter(p -> p.toString().endsWith(".java"))
                    .map(p -> {
                        try {
                            return Files.readString(p);
                        } catch (Exception e) {
                            return "";
                        }
                    })
                    .collect(Collectors.joining("\n\n"));

        } catch (Exception e) {
            return "";
        }
    }
}