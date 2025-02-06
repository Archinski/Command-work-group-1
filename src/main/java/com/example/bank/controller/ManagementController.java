package com.example.bank.controller;

import com.example.bank.service.RecommendationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagementController {

    private final RecommendationService recommendationService;
    private final BuildProperties buildProperties;

    @Value("${spring.application.name}")
    private String serviceName;

    public ManagementController(RecommendationService recommendationService, BuildProperties buildProperties) {
        this.recommendationService = recommendationService;
        this.buildProperties = buildProperties;
    }

    @PostMapping("/clear-caches")
    public ResponseEntity<Void> clearCaches() {
        recommendationService.clearCache();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<ServiceInfo> getServiceInfo() {
        return ResponseEntity.ok(new ServiceInfo(serviceName, buildProperties.getVersion()));
    }

    record ServiceInfo(String name, String version) {
    }
}
