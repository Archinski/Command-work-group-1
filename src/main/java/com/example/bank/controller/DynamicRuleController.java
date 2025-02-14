package com.example.bank.controller;

import com.example.bank.rulesdb.DynamicRule;
import com.example.bank.service.DynamicRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    @Autowired
    private DynamicRuleService dynamicRuleService;

    @PostMapping
    public ResponseEntity<DynamicRule> createRule(@RequestBody DynamicRule rule) {
        DynamicRule createdRule = dynamicRuleService.createRule(rule);
        return ResponseEntity.ok(createdRule);
    }

    @GetMapping
    public ResponseEntity<List<DynamicRule>> getAllRules() {
        return ResponseEntity.ok(dynamicRuleService.getAllRules());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        dynamicRuleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}
