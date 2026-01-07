package com.testpilot.ai;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NLPProcessor {

    public static List<String> preprocess(String manualText) {

        return Arrays.stream(manualText.split("\\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}

