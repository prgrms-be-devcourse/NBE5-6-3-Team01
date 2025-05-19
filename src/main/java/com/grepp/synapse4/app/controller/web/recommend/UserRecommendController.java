package com.grepp.synapse4.app.controller.web.recommend;

import com.grepp.synapse4.app.model.llm.GeminiUserRecommendPromptService;
import com.grepp.synapse4.app.model.llm.dto.userrecommenddto.GeminiResponseDto;
import com.grepp.synapse4.app.model.llm.dto.userrecommenddto.RecommendedRestaurantDto;
import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.app.model.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserRecommendController {

    private final GeminiUserRecommendPromptService geminiUserRecommendPromptService;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/recommend")
    public String recommendStarter() {
        return "/recommend/recommendLlm";
    }


    @GetMapping("/recommend/result")
    public String showRecommendationResult(@RequestParam("questionText") String questionText, Model model) {

        GeminiResponseDto response = geminiUserRecommendPromptService.generateRecommendations(questionText);

        List<RecommendedRestaurantDto> results = response.getRecommendations().stream()
                .map(rec -> {
                    Long id = rec.getRestaurantId();
                    Restaurant restaurant = restaurantRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("식당 없음 (id=" + id + ")"));
                    return new RecommendedRestaurantDto(restaurant, rec.getReason());
                })
                .collect(Collectors.toList());

        model.addAttribute("results", results);
        model.addAttribute("questionText", questionText);

        return "recommend/recommendResult";
    }
}
