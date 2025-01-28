package com.example.bank.controller;

import com.example.bank.dto.RecommendationResponse;
import com.example.bank.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendation/{user_id}")
    public RecommendationResponse getRecommendations(@PathVariable("user_id") UUID userId) {
        return recommendationService.getRecommendations(userId.toString());
    }
}
