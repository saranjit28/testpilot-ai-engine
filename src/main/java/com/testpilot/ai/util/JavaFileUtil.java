package com.testpilot.ai.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaFileUtil {

    public static boolean exists(Path file) {
        return Files.exists(file);
    }

    public static String read(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException e) {
            return "";
        }
    }

    public static void writeIfAbsent(Path file, String content) {
        try {
            if (!Files.exists(file)) {
                Files.createDirectories(file.getParent());
                Files.writeString(file, content);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file " + file, e);
        }
    }

    public static void appendBeforeClassEnd(Path file, String methodBlock) {
        try {
            String content = Files.readString(file);
            int idx = content.lastIndexOf("}");
            if (idx == -1) return;

            String updated =
                    content.substring(0, idx)
                            + "\n"
                            + methodBlock
                            + "\n}";

            Files.writeString(file, updated);
        } catch (IOException e) {
            throw new RuntimeException("Failed to append method", e);
        }
    }

    public static boolean methodExists(Path file, String methodName) {
        try {
            String content = Files.readString(file);
            return content.contains("void " + methodName + "(");
        } catch (IOException e) {
            return false;
        }
    }
}