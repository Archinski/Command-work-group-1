package com.example.bank.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionRepositoryTest {

    private TransactionRepository transactionRepository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        transactionRepository = new TransactionRepository(jdbcTemplate);
    }

    @Test
    void usesProductType_ShouldReturnTrue_WhenProductExists() {
        when(jdbcTemplate.queryForObject(any(String.class), any(Class.class), any(), any()))
                .thenReturn(1);

        boolean result = transactionRepository.usesProductType("user123", "DEBIT");
        assertTrue(result);
    }

    @Test
    void getTotalDepositsByType_ShouldReturnCorrectSum() {
        when(jdbcTemplate.queryForObject(any(String.class), any(Class.class), any(), any()))
                .thenReturn(1000.0);

        double result = transactionRepository.getTotalDepositsByType("user123", "DEBIT");
        assertEquals(1000.0, result);
    }

    @Test
    void getTotalSpendsByType_ShouldReturnCorrectSum() {
        when(jdbcTemplate.queryForObject(any(String.class), any(Class.class), any(), any()))
                .thenReturn(500.0);

        double result = transactionRepository.getTotalSpendsByType("user123", "DEBIT");
        assertEquals(500.0, result);
    }
}
