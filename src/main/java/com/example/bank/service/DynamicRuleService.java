package com.example.bank.service;

import com.example.bank.jpa.DynamicRuleRepository;
import com.example.bank.rulesdb.DynamicRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicRuleService {

    @Autowired
    private DynamicRuleRepository dynamicRuleRepository;

    public DynamicRule createRule(DynamicRule dynamicRule) {
        return dynamicRuleRepository.save(dynamicRule);
    }

    public List<DynamicRule> getAllRules() {
        return dynamicRuleRepository.findAll();
    }

    public void deleteRule(Long id) {
        dynamicRuleRepository.deleteById(id);
    }
}
