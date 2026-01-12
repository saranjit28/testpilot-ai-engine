package com.testpilot.runner;

import com.testpilot.azure.StepsFileLister;
import com.testpilot.ai.export.DummyStepsWriter;
import com.testpilot.ai.model.StepDefinition;

import java.nio.file.Path;
import java.util.List;

public class ListGherkinStepsRunner {

    public static void main(String[] args) throws Exception {

        System.out.println("🚀 Fetching steps from TFS...");
        List<StepDefinition> steps =
                StepsFileLister.fetchAllSteps();

        System.out.println("📦 Total steps fetched = " + steps.size());

        Path out = Path.of("target/testpilot-all-steps.txt");

        DummyStepsWriter.write(steps, out);

        System.out.println("✅ Steps written to: " + out.toAbsolutePath());
    }
}
