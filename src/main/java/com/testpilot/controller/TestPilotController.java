package com.testpilot.controller;

import com.testpilot.dto.AnalyzeRequest;
import com.testpilot.ai.model.TestPilotResponse;
import com.testpilot.service.AnalyzeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testpilot")
@CrossOrigin(origins = "*")
public class TestPilotController {

    private final AnalyzeService analyzeService;

    public TestPilotController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<TestPilotResponse> analyze(
            @RequestBody AnalyzeRequest request) {

        try {
            return ResponseEntity.ok(
                    analyzeService.analyzeManualTestCase(request)
            );
        } catch (Exception e) {

            TestPilotResponse error =
                    TestPilotResponse.build(
                            request.getRequestId(),
                            request.getTestName(),
                            "ERROR",
                            List.of(),
                            List.of(),
                            List.of("Internal error: " + e.getMessage())
                    );
            return ResponseEntity.status(200).body(error);
        }
    }

}
