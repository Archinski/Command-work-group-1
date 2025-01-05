package com.example.bank.controller;

import com.example.bank.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/management")
public class ManagementController {

    private final RecommendationService recommendationService;

    public ManagementController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/clear-caches")
    public ResponseEntity<Void> clearCaches() {
        recommendationService.clearCaches();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getServiceInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", "RecommendationService");
        info.put("version", "1.0.0"); // Версия вытягивается из POM (позже можно автоматизировать)
        return ResponseEntity.ok(info);
    }
}
