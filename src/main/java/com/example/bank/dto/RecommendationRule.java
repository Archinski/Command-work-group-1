package com.example.bank.dto;

public class RecommendationRule {
    private String ruleId;
    private String description;

    public RecommendationRule(String ruleId, String description) {
        this.ruleId = ruleId;
        this.description = description;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
