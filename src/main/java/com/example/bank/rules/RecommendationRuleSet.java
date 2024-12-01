package com.example.bank.rules;

import com.example.bank.dto.RecommendationResponse;

import java.util.Optional;

public interface RecommendationRuleSet {
    Optional<RecommendationResponse.Recommendation> apply(String userId);
}
