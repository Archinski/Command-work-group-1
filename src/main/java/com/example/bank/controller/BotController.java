package com.example.bank.controller;

import com.example.bank.dto.RuleStatistic;
import com.example.bank.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class BotController {

    private final RecommendationService recommendationService;
    private final CacheManager cacheManager;

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Autowired
    public BotController(RecommendationService recommendationService, CacheManager cacheManager) {
        this.recommendationService = recommendationService;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/rule/stats")
    public ResponseEntity<List<RuleStatistic>> getStats() {
        try {
            List<RuleStatistic> stats = recommendationService.getAllRuleStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            log.error("Ошибка при получении статистики правил", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/management/clear-caches")
    public ResponseEntity<String> clearCaches() {
        try {
            // 1. Получаем имена всех доступных кэшей
            for (String cacheName : cacheManager.getCacheNames()) {
                // 2. Получаем экземпляр кэша по имени
                Cache cache = cacheManager.getCache(cacheName);

                // 3. Проверяем, что кэш существует (на всякий случай)
                if (cache != null) {
                    // 4. Очищаем кэш
                    cache.clear();
                    log.info("Кэш '{}' успешно очищен.", cacheName);
                } else {
                    log.warn("Кэш с именем '{}' не найден.", cacheName);
                }
            }
            return ResponseEntity.ok("Все кэши успешно очищены.");

        } catch (Exception e) {
            log.error("Ошибка при очистке кэшей", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при очистке кэшей.");
        }
    }

    @GetMapping("/management/info")
    public ResponseEntity<String> getInfo() {
        try {
            String info = String.format("{ \"name\": \"%s\", \"version\": \"%s\" }", appName, appVersion);
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            log.error("Ошибка при получении информации о приложении", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при получении информации.");
        }
    }
}
