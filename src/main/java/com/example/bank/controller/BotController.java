package com.example.bank.controller;

import com.example.bank.dto.RuleStatistic;
import com.example.bank.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BotController {

    private final RecommendationService recommendationService;

    @Autowired
    public BotController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/rule/stats")
    public List<RuleStatistic> getStats() {
        return recommendationService.getAllRuleStatistics();
    }

    @PostMapping("/management/clear-caches")
    public void clearCaches() {
        // Логика сброса кеша
    }

    @GetMapping("/management/info")
    public String getInfo() {
        return "{ \"name\": \"TelegramBot\", \"version\": \"1.0.0\" }";
    }
}
