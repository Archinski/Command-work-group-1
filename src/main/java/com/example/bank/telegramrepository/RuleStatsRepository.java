package com.example.bank.telegramrepository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RuleStatsRepository {

    private final Map<String, Integer> ruleStats = new ConcurrentHashMap<>();

    public void incrementRuleStat(String ruleId) {
        ruleStats.merge(ruleId, 1, Integer::sum);
    }

    public Map<String, Integer> getAllRuleStats() {
        return new HashMap<>(ruleStats);
    }
}