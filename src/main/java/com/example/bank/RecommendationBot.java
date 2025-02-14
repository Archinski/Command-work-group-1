package com.example.bank;

import com.example.bank.service.RecommendationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class RecommendationBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    private final RecommendationService recommendationService;


    public RecommendationBot(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }
    @Override
    public String getBotUsername() {
        return botUsername;
    }
    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if (messageText.equals("/start")) {
                sendWelcomeMessage(chatId);
            } else if (messageText.startsWith("/recommend")) {
                handleRecommendationCommand(messageText, chatId);
            }
        }
    }

    private void handleRecommendationCommand(String messageText, String chatId) {
        String[] parts = messageText.split(" ", 2);
        if(parts.length != 2){
            sendMessage(chatId, "Некорректная команда, используйте: /recommend username");
            return;
        }
        String username = parts[1];
        String recommendations = recommendationService.generateRecommendation(username);
        sendMessage(chatId, recommendations);
    }

    private void sendWelcomeMessage(String chatId) {
        String welcomeMessage = "Здравствуйте! Я бот для получения рекомендаций.\n" +
                "Используйте команду /recommend username для получения персональных рекомендаций.\n" +
                "Пример: /recommend John ";
        sendMessage(chatId, welcomeMessage);
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
