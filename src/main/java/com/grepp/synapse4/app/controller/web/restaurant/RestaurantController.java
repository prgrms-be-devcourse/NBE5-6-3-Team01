package com.grepp.synapse4.app.controller.web.restaurant;

import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.app.model.restaurant.entity.RestaurantMenu;
import com.grepp.synapse4.app.model.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    @Value("${kakao.maps.api-key}")
    private String kakaoMapApiKey;

    @GetMapping("restaurant/detail/{id}")
    public String restaurantDetail(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantRepository.findWithMenusById(id)
                .orElseThrow(() -> new IllegalArgumentException("식당 없음"));

        // 메뉴 가장 비싼 메뉴 상위 3개만 노출
        List<RestaurantMenu> topMenus = restaurant.getMenus().stream()
                .sorted((a, b) -> b.getPrice() - a.getPrice())
                .limit(3)
                .toList();

        model.addAttribute("kakaoMapApiKey", kakaoMapApiKey);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("topMenus", topMenus);
        return "restaurant/detail";
    }
}
