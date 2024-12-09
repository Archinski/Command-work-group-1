package com.example.bank.service;

import com.example.bank.dto.RecommendationResponse;
import com.example.bank.rules.RecommendationRuleSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecommendationServiceTest {

    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        RecommendationRuleSet mockRule1 = mock(RecommendationRuleSet.class);
        RecommendationRuleSet mockRule2 = mock(RecommendationRuleSet.class);

        when(mockRule1.apply("user123"))
                .thenReturn(Optional.of(new RecommendationResponse.Recommendation("Invest 500", "123", "Description")));
        when(mockRule2.apply("user123"))
                .thenReturn(Optional.empty());

        recommendationService = new RecommendationService(List.of(mockRule1, mockRule2));
    }

    @Test
    void getRecommendations_ShouldReturnListOfRecommendations() {
        RecommendationResponse response = recommendationService.getRecommendations("user123");

        assertEquals(1, response.getRecommendations().size());
        assertEquals("Invest 500", response.getRecommendations().get(0).getName());
    }

}
