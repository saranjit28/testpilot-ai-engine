package com.testpilot.ai.util;

import com.testpilot.ai.model.StepDefinition;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.util.List;

public class JsonWriterUtil {

    public static void writeToJson(List<StepDefinition> steps, String filePath) {

        JSONArray array = new JSONArray();

        for (StepDefinition step : steps) {
            JSONObject obj = new JSONObject();
            obj.put("keyword", step.getKeyword());
            obj.put("step", step.getStepText());
            obj.put("file", step.getFileName());
            array.put(obj);
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(array.toString(4));
            System.out.println("JSON generated at: " + filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
