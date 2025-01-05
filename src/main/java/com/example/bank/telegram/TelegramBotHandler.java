package com.example.bank.telegram;

import com.example.bank.dto.RecommendationResponse;
import com.example.bank.service.RecommendationService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@Component
public class TelegramBotHandler extends TelegramLongPollingBot {

    private final RecommendationService recommendationService;

    public TelegramBotHandler(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/recommend")) {
                String username = messageText.replace("/recommend", "").trim();
                handleRecommendation(chatId, username);
            } else {
                sendMessage(chatId, "Команда не распознана. Используйте /recommend <имя пользователя>.");
            }
        }
    }

    private void handleRecommendation(Long chatId, String username) {
        String userId = recommendationService.findUserIdByUsername(username);
        if (userId != null) {
            RecommendationResponse response = recommendationService.getRecommendations(userId);
            if (!response.getRecommendations().isEmpty()) {
                String recommendations = response.getRecommendations().stream()
                        .map(r -> String.format("- %s: %s", r.getTitle(), r.getDescription()))
                        .collect(Collectors.joining("\n"));

                String reply = String.format("Здравствуйте, %s!\n\nНовые продукты для вас:\n%s",
                        username, recommendations);
                sendMessage(chatId, reply);
            } else {
                sendMessage(chatId, "Для вас пока нет рекомендаций.");
            }
        } else {
            sendMessage(chatId, "Пользователь не найден.");
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        try {
            execute(message);
        } catch (Exception e) {
            System.err.printf("Ошибка при отправке сообщения в чат %d: %s%n", chatId, e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return "Ami_jun_bot";
    }

    @Override
    public String getBotToken() {
        return "7155642242:AAGIqgnNOpEt_gc7Qg2WGJn8Vk7skc6r6eU";
    }
}

