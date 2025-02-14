package com.example.bank.repository;

import com.example.bank.dto.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findAllByUserId(Long userId);
    List<Recommendation> findByUserUsername(String username);
    void deleteByRuleId(String ruleId);
}
