package com.testpilot.runner;

import com.testpilot.ai.StepPromptBuilder;
import com.testpilot.ai.model.StepDefinition;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AIPromptRunner {

    public static void main(String[] args) throws Exception {

        String json = Files.readString(Paths.get("steps-output.json"));

        JSONArray array = new JSONArray(json);
        List<StepDefinition> steps = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {

            JSONObject obj = array.getJSONObject(i);
        }

        String prompt = StepPromptBuilder.buildPrompt(steps);

        Files.writeString(Paths.get("ai-prompt.txt"), prompt);

        System.out.println("✅ AI Prompt generated: ai-prompt.txt");
    }
}

