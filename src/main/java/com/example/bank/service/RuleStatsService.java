package com.example.bank.service;

import com.example.bank.telegramrepository.RuleStatsRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RuleStatsService {

    private final RuleStatsRepository ruleStatsRepository;

    public RuleStatsService(RuleStatsRepository ruleStatsRepository) {
        this.ruleStatsRepository = ruleStatsRepository;
    }

    public void incrementRuleStat(String ruleId) {
        ruleStatsRepository.incrementRuleStat(ruleId);
    }

    public Map<String, Integer> getAllRuleStats() {
        return ruleStatsRepository.getAllRuleStats();
    }
}

