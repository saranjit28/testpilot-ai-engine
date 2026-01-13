package com.testpilot.ai.llm;

public class SeleniumPromptBuilder {

    public static String build(
            String stepText,
            String pageName,
            String methodName
    ) {

        return """
                Generate ONLY the Java method body for the following PageObject method.

                Rules:
                - Use Selenium WebDriver
                - Use driver.findElement(By...)
                - Do NOT include class, imports, annotations, or method signature
                - Do NOT add comments
                - If unsure, throw new RuntimeException("Implementation required")
                - Assume WebDriver variable name is: driver

                PageObject: %s
                Method name: %s
                Step description: %s
                """.formatted(pageName, methodName, stepText);
    }
}
