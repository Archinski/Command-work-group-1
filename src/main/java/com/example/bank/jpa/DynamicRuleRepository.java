package com.example.bank.jpa;

import com.example.bank.rulesdb.DynamicRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long> {
}
