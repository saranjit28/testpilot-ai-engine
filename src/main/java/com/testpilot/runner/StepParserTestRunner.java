package com.testpilot.runner;

import com.testpilot.ai.model.StepDefinition;
import com.testpilot.parser.StepDefinitionParser;

import java.util.List;

public class StepParserTestRunner {

    public static void run(String javaSource, String fileName) {

        List<StepDefinition> steps =
                StepDefinitionParser.parse(javaSource, fileName);

        for (StepDefinition step : steps) {
            System.out.println(step);
        }
    }
}
