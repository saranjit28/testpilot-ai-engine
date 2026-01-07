package com.testpilot.ai.page;

import com.testpilot.ai.util.JavaFileUtil;
import com.testpilot.ai.util.MethodNameResolver;

import java.nio.file.Path;

public class PageObjectClassGenerator {

    private PageObjectClassGenerator() {
        // utility class
    }

    public static void generateOrAppend(
            Path file,
            String packageName,
            String pageName,
            String methodBlock,
            String methodName
    ) {

        // Case 1: PageObject does not exist → create it
        if (!JavaFileUtil.exists(file)) {

            String content =
                    "package " + packageName + ";\n\n"
                            + "public class " + pageName + " {\n\n"
                            + methodBlock
                            + "\n}\n";

            JavaFileUtil.writeIfAbsent(file, content);
            return;
        }

        // Case 2: PageObject exists → append safely
        String resolvedMethodName =
                MethodNameResolver.resolve(file, methodName);

        // If name changed, update method block
        if (!resolvedMethodName.equals(methodName)) {
            methodBlock =
                    methodBlock.replace(
                            "void " + methodName + "(",
                            "void " + resolvedMethodName + "("
                    );
        }

        JavaFileUtil.appendBeforeClassEnd(file, methodBlock);
    }
}