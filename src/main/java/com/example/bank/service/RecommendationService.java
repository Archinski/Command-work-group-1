package com.example.bank.service;

import com.example.bank.dto.RecommendationResponse;
import com.example.bank.rules.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> rules;

    public RecommendationService(List<RecommendationRuleSet> rules) {
        this.rules = rules;
    }

    public RecommendationResponse getRecommendations(String userId) {
        RecommendationResponse response = new RecommendationResponse();
        response.setUserId(userId);

        List<RecommendationResponse.Recommendation> recommendations = rules.stream()
                .map(rule -> rule.apply(userId))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        response.setRecommendations(recommendations);
        return response;
    }

}
