package com.testpilot.ai.util;

import java.nio.file.Path;

public class MethodNameResolver {

    private MethodNameResolver() {
        // utility class
    }

    /**
     * Resolves method name collisions inside a Java file.
     * If method already exists, suffixes with _2, _3, ...
     */
    public static String resolve(
            Path file,
            String baseMethodName
    ) {

        if (!JavaFileUtil.exists(file)) {
            return baseMethodName;
        }

        if (!JavaFileUtil.methodExists(file, baseMethodName)) {
            return baseMethodName;
        }

        int counter = 2;
        while (true) {
            String candidate = baseMethodName + "_" + counter;
            if (!com.testpilot.ai.util.JavaFileUtil.methodExists(file, candidate)) {
                return candidate;
            }
            counter++;
        }
    }
}