package com.example.bank.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.User;

@Entity
@Table(name = "recommendations")
@Data
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String ruleId;
    @Getter
    private String product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Recommendation(String description, String ruleId, User user) {
        this.description = description;
        this.ruleId = ruleId;
        this.user = user;
    }
    public Recommendation(String description, String ruleId, com.example.bank.dto.User user) {

    }
}
