package com.testpilot.ai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "UP";
    }

    @GetMapping("/")
    public String home() {
        return "✅ TestPilot AI Backend is running";
    }
}