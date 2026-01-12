package com.testpilot.controller;

import com.testpilot.dto.AnalyzeRequest;
import com.testpilot.ai.model.TestPilotResponse;
import com.testpilot.service.AnalyzeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testpilot")
public class TestPilotAnalyzeController {

    private final AnalyzeService analyzeService;

    public TestPilotAnalyzeController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @PostMapping("/analyze")
    public TestPilotResponse analyze(
            @RequestBody AnalyzeRequest request
    ) {
        return analyzeService.analyzeManualTestCase(request);
    }
}