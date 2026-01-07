package com.testpilot;

import java.util.ArrayList;
import java.util.List;

public class GherkinGenerator {

    public static List<String> generate(List<String> steps) {

        List<String> gherkin = new ArrayList<>();

        for (String step : steps) {

            if (step.contains("login") || step.contains("open")) {
                gherkin.add("Given " + step);
            }
            else if (step.contains("click") || step.contains("select")) {
                gherkin.add("When " + step);
            }
            else {
                gherkin.add("Then " + step);
            }
        }
        return gherkin;
    }
}
