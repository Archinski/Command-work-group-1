package com.example.bank.service;

import com.example.bank.jpa.DynamicRuleRepository;
import com.example.bank.rulesdb.DynamicRule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicRuleService {

    private final DynamicRuleRepository dynamicRuleRepository;

    public DynamicRuleService(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    public DynamicRule createRule(DynamicRule dynamicRule) {
        return dynamicRuleRepository.save(dynamicRule);
    }

    public List<DynamicRule> getAllRules() {
        return new ArrayList<>(dynamicRuleRepository.findAll());
    }

    public void deleteRule(Long id) {
        dynamicRuleRepository.deleteById(id);
    }
}

