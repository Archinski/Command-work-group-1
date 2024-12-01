package com.example.bank.rules;

import com.example.bank.dto.RecommendationResponse;
import com.example.bank.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SimpleCreditRule implements RecommendationRuleSet{

    private final TransactionRepository transactionRepository;

    public SimpleCreditRule(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<RecommendationResponse.Recommendation> apply(String userId) {
        boolean usesCredit = transactionRepository.usesProductType(userId, "CREDIT");
        double debitDeposits = transactionRepository.getTotalDepositsByType(userId, "DEBIT");
        double debitSpends = transactionRepository.getTotalSpendsByType(userId, "DEBIT");

        if (!usesCredit && debitDeposits > debitSpends && debitSpends > 100000) {
            return Optional.of(new RecommendationResponse.Recommendation(
                    "Simple Credit",
                    "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                    "Откройте мир выгодных кредитов с нами!"
            ));
        }
        return Optional.empty();
    }
}
