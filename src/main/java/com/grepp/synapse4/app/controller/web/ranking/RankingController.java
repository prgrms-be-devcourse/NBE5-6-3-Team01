package com.grepp.synapse4.app.controller.web.ranking;

import com.grepp.synapse4.app.model.restaurant.RestaurantService;
import com.grepp.synapse4.app.model.user.dto.RankingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {


    private final RestaurantService restaurantService;

    @GetMapping
    public String ranking(Model model) {
        List<RankingDto> ranking = restaurantService.getRestaurantRanking();
        model.addAttribute("ranking", ranking);
        return "ranking/ranking";
    }
}
