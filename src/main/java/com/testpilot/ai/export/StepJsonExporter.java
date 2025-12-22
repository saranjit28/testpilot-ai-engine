package com.testpilot.ai.export;

import com.testpilot.ai.model.StepDefinition;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.util.List;

public class StepJsonExporter {

    public static void export(List<StepDefinition> steps, String outputFile) {

        JSONArray array = new JSONArray();

        for (StepDefinition step : steps) {

            JSONObject obj = new JSONObject();
            obj.put("keyword", step.getKeyword());
            obj.put("step", step.getStepText());
            obj.put("file", step.getFileName());

            array.put(obj);
        }

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(array.toString(2)); // pretty print
        } catch (Exception e) {
            throw new RuntimeException("Failed to write JSON", e);
        }
    }
}
