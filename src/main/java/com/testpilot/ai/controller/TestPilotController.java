package com.testpilot.ai.controller;

import com.testpilot.ai.engine.TestPilotAIEngine;
import com.testpilot.ai.model.TestPilotRequest;
import com.testpilot.ai.model.TestPilotResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/testpilot")
public class TestPilotController {

    @PostMapping("/analyze")
    public TestPilotResponse analyze(
            @RequestBody TestPilotRequest request) {

        return TestPilotAIEngine.run(
                request.getManualTestCase()
        );
    }
}
