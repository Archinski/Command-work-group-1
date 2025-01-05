package com.example.bank.controller;

import com.example.bank.service.RuleStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rule")
public class RuleStatsController {

    private final RuleStatsService ruleStatsService;

    public RuleStatsController(RuleStatsService ruleStatsService) {
        this.ruleStatsService = ruleStatsService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Integer>> getRuleStats() {
        return ResponseEntity.ok(ruleStatsService.getAllRuleStats());
    }
}

