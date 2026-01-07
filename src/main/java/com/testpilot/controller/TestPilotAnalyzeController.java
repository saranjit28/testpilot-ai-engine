package com.testpilot.controller;

import com.testpilot.ai.model.TestPilotResponse;
import org.springframework.web.bind.annotation.*;
import com.testpilot.dto.AnalyzeRequest;
import com.testpilot.service.AnalyzeService;

@RestController
@RequestMapping("/api/testpilot")
public class TestPilotAnalyzeController {

    private final AnalyzeService analyzeService;

    public TestPilotAnalyzeController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @PostMapping("/api/testpilot/analyze")
    public TestPilotResponse analyze(@RequestBody AnalyzeRequest request) {
        return analyzeService.analyzeManualTestCase(request);
    }

}
