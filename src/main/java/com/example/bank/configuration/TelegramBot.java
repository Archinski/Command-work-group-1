package com.example.bank.configuration;

import com.example.bank.dto.Recommendation;
import com.example.bank.dto.User;
import com.example.bank.repository.RecommendationRepository;
import com.example.bank.repository.UserReposit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String botToken;
    private final String botUsername;
    private final UserReposit userReposit;
    private final RecommendationRepository recommendationRepository;


    public TelegramBot(@Value("${telegram.bot.token}") String botToken,
                       @Value("${telegram.bot.username}") String botUsername,
                       UserReposit userReposit, RecommendationRepository recommendationRepository) {
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.userReposit = userReposit;
        this.recommendationRepository = recommendationRepository;
    }


    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String command = message.getText();
            if (command.startsWith("/recommend")) {
                String username = command.substring("/recommend".length()).trim();
                String recommendations = getRecommendationsForUser(username);
                sendMessage(message.getChatId(), recommendations);
            } else {
                sendMessage(message.getChatId(), "Привет! Используй команду /recommend <username> для получения рекомендаций.");
            }
        }
    }

    private String getRecommendationsForUser(String username) {
        User user = userReposit.findByUsername(username);
        if (user != null) {
            List<Recommendation> recommendations = recommendationRepository.findByUserUsername(username);
            if (recommendations.isEmpty()) {
                return "Здравствуйте, " + username + "\nУ вас пока нет персональных рекомендаций.";
            }
            StringBuilder sb = new StringBuilder("Здравствуйте, " + username + "\nРекомендации для вас:\n");
            for (Recommendation rec : recommendations) {
                sb.append("- ").append(rec.getProduct()).append(": ").append(rec.getDescription()).append("\n");
            }
            return sb.toString();

        } else {
            return "Пользователь не найден";
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
