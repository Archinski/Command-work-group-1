package com.example.bank.rulesdb;

import jakarta.persistence.*;

@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_text")
    private String productText;

    @Column(name = "rule_json", columnDefinition = "TEXT")
    private String ruleJson;
}
