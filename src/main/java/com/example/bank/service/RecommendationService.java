package com.example.bank.service;

import com.example.bank.dto.Recommendation;
import com.example.bank.dto.RecommendationResponse;
import com.example.bank.dto.RuleStatistic;
import com.example.bank.dto.User;
import com.example.bank.repository.RecommendationRepository;
import com.example.bank.repository.RuleStatisticRepository;
import com.example.bank.repository.UserReposit;
import com.example.bank.rules.RecommendationRuleSet;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final UserReposit userRepository;
    private final RecommendationRepository recommendationRepository;
    private final RuleStatisticRepository ruleStatisticRepository;
    private final List<RecommendationRuleSet> rules;

    public RecommendationService(UserReposit userRepository, RecommendationRepository recommendationRepository, RuleStatisticRepository ruleStatisticRepository, List<RecommendationRuleSet> rules) {
        this.userRepository = userRepository;
        this.recommendationRepository = recommendationRepository;
        this.ruleStatisticRepository = ruleStatisticRepository;
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

    public String generateRecommendation(String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByFirstName(username));
        if (userOptional.isEmpty()) {
            return "Пользователь не найден";
        }

        User user = userOptional.get();
        List<Recommendation> recommendations = recommendationRepository.findAllByUserId(user.getId());
        if (recommendations.isEmpty()) {
            return "Нет рекомендаций для пользователя " + user.getFirstName() + " " + user.getLastName();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Здравствуйте ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("\n");
        sb.append("Новые продукты для вас:\n");
        for (Recommendation recommendation : recommendations) {
            sb.append("- ").append(recommendation.getDescription()).append("\n");
        }

        return sb.toString();
    }

    public void addRecommendationToUser(User user, String description, String ruleId) {
        Recommendation recommendation = new Recommendation(description, ruleId, user);
        recommendationRepository.save(recommendation);
    }

    public void incrementRuleStatistic(String ruleId) {
        Optional<RuleStatistic> ruleStatisticOptional = ruleStatisticRepository.findById(ruleId);
        if (ruleStatisticOptional.isPresent()) {
            RuleStatistic ruleStatistic = ruleStatisticOptional.get();
            ruleStatistic.setCount(ruleStatistic.getCount() + 1);
            ruleStatisticRepository.save(ruleStatistic);
        } else {
            ruleStatisticRepository.save(new RuleStatistic(ruleId, 1));
        }
    }

    public void deleteRuleStatistic(String ruleId) {
        ruleStatisticRepository.deleteById(ruleId);
        recommendationRepository.deleteByRuleId(ruleId);
    }

    public List<RuleStatistic> getAllRuleStatistics() {
        List<RuleStatistic> statistics = ruleStatisticRepository.findAll();
        return statistics;
    }

    @CacheEvict(value = "recommendations", allEntries = true)
    public void clearCache() {
        System.out.println("Cache cleared");
    }
}
