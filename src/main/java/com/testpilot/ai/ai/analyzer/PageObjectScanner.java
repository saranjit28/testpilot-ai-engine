package com.testpilot.ai.ai.analyzer;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class PageObjectScanner {

    public static Map<String, List<String>> scan(Path mainJavaPath) {

        Map<String, List<String>> pageMethods = new HashMap<>();

        try {
            Files.walk(mainJavaPath)
                    .filter(p -> p.toString().endsWith(".java"))
                    .filter(p -> p.toString().toLowerCase().contains("page"))
                    .forEach(file -> {

                        String className =
                                file.getFileName().toString().replace(".java", "");

                        List<String> methods =
                                extractMethods(file);

                        if (!methods.isEmpty()) {
                            pageMethods.put(className, methods);
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pageMethods;
    }

    private static List<String> extractMethods(Path file) {

        try {
            return Files.readAllLines(file).stream()
                    .filter(l -> l.matches(".*void\\s+\\w+\\s*\\(.*\\).*"))
                    .map(l -> l.replaceAll(".*void", "")
                            .replaceAll("\\(.*", "")
                            .trim())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
