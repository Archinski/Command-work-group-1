package com.example.bank.controller;

import com.example.bank.dto.RuleStatistic;
import com.example.bank.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rule")
public class RuleStatisticController {

    private final RecommendationService recommendationService;

    public RuleStatisticController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }
    @GetMapping("/stats")
    public ResponseEntity<RuleStatsResponse> getRuleStatistics(){
        List<RuleStatistic> ruleStatistics = recommendationService.getAllRuleStatistics();
        List<RuleStat> stats = ruleStatistics.stream()
                .map(ruleStatistic -> new RuleStat(ruleStatistic.getRuleId(), ruleStatistic.getCount()))
                .collect(Collectors.toList());
        RuleStatsResponse response = new RuleStatsResponse(stats);
        return ResponseEntity.ok(response);
    }

    record RuleStat(String rule_id, int count){}
    record RuleStatsResponse(List<RuleStat> stats){}
}
