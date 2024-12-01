package com.example.bank.rules;

import com.example.bank.dto.RecommendationResponse;
import com.example.bank.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class SimpleCreditRuleTest {

    private Invest500Rule invest500Rule;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        invest500Rule = new Invest500Rule(transactionRepository);
    }

    @Test
    void apply_ShouldReturnRecommendation_WhenConditionsMet() {
        when(transactionRepository.usesProductType("user123", "DEBIT")).thenReturn(true);
        when(transactionRepository.usesProductType("user123", "INVEST")).thenReturn(false);
        when(transactionRepository.getTotalDepositsByType("user123", "SAVING")).thenReturn(2000.0);

        Optional<RecommendationResponse.Recommendation> result = invest500Rule.apply("user123");

        assertTrue(result.isPresent());
    }
}
