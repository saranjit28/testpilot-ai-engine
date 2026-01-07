package com.testpilot.ai.analyzer;

import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class ApiServiceScanner {

    public static Map<String, List<String>> scan(Path mainJavaPath) {

        Map<String, List<String>> services = new HashMap<>();

        try {
            Files.walk(mainJavaPath)
                    .filter(p -> p.toString().endsWith(".java"))
                    .filter(p -> p.toString().toLowerCase().contains("service")
                            || p.toString().toLowerCase().contains("api")
                            || p.toString().toLowerCase().contains("webservice"))
                    .forEach(file -> {

                        String className =
                                file.getFileName().toString().replace(".java", "");

                        List<String> methods =
                                extractMethods(file);

                        if (!methods.isEmpty()) {
                            services.put(className, methods);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return services;
    }

    private static List<String> extractMethods(Path file) {

        try {
            return Files.readAllLines(file).stream()
                    .filter(l -> l.matches(".*\\w+\\s+\\w+\\(.*\\).*"))
                    .filter(l -> l.contains("Response") || l.contains("call"))
                    .map(l -> l.replaceAll(".*\\s", "")
                            .replaceAll("\\(.*", "")
                            .trim())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
