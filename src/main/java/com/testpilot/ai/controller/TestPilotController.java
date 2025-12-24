package com.testpilot.ai.controller;

import com.testpilot.ai.dto.AnalyzeRequest;
import com.testpilot.ai.model.TestPilotResponse;
import com.testpilot.ai.service.AnalyzeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/testpilot")
@CrossOrigin(origins = "*")
public class TestPilotController {

    private final AnalyzeService analyzeService;

    public TestPilotController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @PostMapping(
            value = "/analyze",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TestPilotResponse analyze(@RequestBody AnalyzeRequest request) {
        return analyzeService.analyzeManualTestCase(request);
    }
}
