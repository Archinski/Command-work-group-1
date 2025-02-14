package com.example.bank.repository;

import com.example.bank.dto.RuleStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleStatisticRepository extends JpaRepository<RuleStatistic, String> {
}
