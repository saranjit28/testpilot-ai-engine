package com.testpilot.ai.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeneratedFileWriter {

    public static void write(
            Path baseDir,
            String fileName,
            String content
    ) {

        try {
            Files.createDirectories(baseDir);
            Files.writeString(baseDir.resolve(fileName), content);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to write generated file: " + fileName, e
            );
        }
    }
}
