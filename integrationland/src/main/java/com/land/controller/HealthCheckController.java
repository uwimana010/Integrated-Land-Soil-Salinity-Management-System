package com.land.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {

    @GetMapping
    public Map<String, String> healthCheck() {
        return Map.of(
                "status", "UP",
                "message", "Integrated Land, Soil & Salinity Management System Backend is running",
                "phase", "Phase 5: Backend Project Setup");
    }
}
