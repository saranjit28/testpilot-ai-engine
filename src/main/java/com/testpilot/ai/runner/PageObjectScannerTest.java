package com.testpilot.ai.runner;

import com.testpilot.ai.ai.analyzer.PageObjectScanner;

import java.nio.file.Path;
import java.util.Map;
import java.util.List;

public class PageObjectScannerTest {

    public static void main(String[] args) {

        Path mainJava =
                Path.of("D:/New Project/OSPI/govgrants-ospi-automation/");

        Map<String, List<String>> pages =
                PageObjectScanner.scan(mainJava);

        pages.forEach((page, methods) -> {
            System.out.println("\n📄 " + page);
            methods.forEach(m -> System.out.println("  • " + m));
        });
    }
}
