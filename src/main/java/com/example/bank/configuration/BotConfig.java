package com.example.bank.configuration;

import com.example.bank.service.RecommendationService;
import com.example.bank.telegram.TelegramBotHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BotConfig {

    @Bean
    public TelegramBotHandler recommendationBot(RecommendationService recommendationService) {
        return new TelegramBotHandler(recommendationService);
    }
}

