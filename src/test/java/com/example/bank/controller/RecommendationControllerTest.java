package com.example.bank.controller;

import com.example.bank.dto.RecommendationResponse;
import com.example.bank.service.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        RecommendationResponse response = new RecommendationResponse();
        response.setUserId("user123");
        response.setRecommendations(List.of(new RecommendationResponse.Recommendation(
                "Invest 500",
                "123",
                "Description"
        )));

        when(recommendationService.getRecommendations("user123")).thenReturn(response);
    }

    @Test
    void getRecommendations_ShouldReturnOkWithRecommendations() throws Exception {
        mockMvc.perform(get("/api/v1/recommendations/user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user123"))
                .andExpect(jsonPath("$.recommendations[0].title").value("Invest 500"));
    }
}
