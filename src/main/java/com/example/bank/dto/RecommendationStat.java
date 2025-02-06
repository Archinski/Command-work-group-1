package com.example.bank.dto;

public class RecommendationStat {
    private String ruleId;
    private int count;

    public RecommendationStat(String ruleId, int count) {
        this.ruleId = ruleId;
        this.count = count;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
