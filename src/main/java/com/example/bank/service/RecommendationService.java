package com.example.bank.service;

import com.example.bank.dto.RecommendationResponse;
import com.example.bank.repository.UserRepository;
import com.example.bank.rules.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> rules;
    private final UserRepository userRepository;

    private final Map<String, List<RecommendationResponse.Recommendation>> cache = new ConcurrentHashMap<>();

    public RecommendationService(List<RecommendationRuleSet> rules, UserRepository userRepository) {
        this.rules = rules;
        this.userRepository = userRepository;
    }

    public String findUserIdByUsername(String username) {
        return userRepository.findUserIdByUsername(username);
    }

    public RecommendationResponse getRecommendations(String userId) {
        if (cache.containsKey(userId)) {
            return (RecommendationResponse) cache.get(userId);
        }

        RecommendationResponse response = new RecommendationResponse();
        response.setUserId(userId);

        List<RecommendationResponse.Recommendation> recommendations = rules.stream()
                .map(rule -> rule.apply(userId))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        response.setRecommendations(recommendations);

        // Кэширование результатов
        cache.put(userId, recommendations);

        return response;
    }

    public void clearCaches() {
        cache.clear();
    }
}

