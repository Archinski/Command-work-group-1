package com.example.bank.rules;

import com.example.bank.dto.RecommendationResponse;
import com.example.bank.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TopSavingRule implements RecommendationRuleSet{

    private final TransactionRepository transactionRepository;

    public TopSavingRule(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<RecommendationResponse.Recommendation> apply(String userId) {
        boolean usesDebit = transactionRepository.usesProductType(userId, "DEBIT");
        double debitDeposits = transactionRepository.getTotalDepositsByType(userId, "DEBIT");
        double savingDeposits = transactionRepository.getTotalDepositsByType(userId, "SAVING");
        double debitSpends = transactionRepository.getTotalSpendsByType(userId, "DEBIT");

        if (usesDebit &&
                (debitDeposits >= 50000 || savingDeposits >= 50000) &&
                debitDeposits > debitSpends) {
            return Optional.of(new RecommendationResponse.Recommendation(
                    "Top Saving",
                    "59efc529-2fff-41af-baff-90ccd7402925",
                    "Откройте свою собственную «Копилку» с нашим банком!"
            ));
        }
        return Optional.empty();
    }
}
