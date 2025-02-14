package com.example.bank.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class RuleStatistic {
    @Id
    private String ruleId;
    private Integer count;

    public RuleStatistic(String ruleId, int count){
        this.ruleId = ruleId;
        this.count = count;
    }
    public RuleStatistic(){

    }
}
