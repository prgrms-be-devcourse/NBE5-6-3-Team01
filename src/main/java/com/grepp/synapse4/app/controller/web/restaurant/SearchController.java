package com.grepp.synapse4.app.controller.web.restaurant;

import com.grepp.synapse4.app.model.restaurant.RestaurantSearchService;
import com.grepp.synapse4.app.model.restaurant.dto.search.SearchRestaurantResponseDto;
import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final RestaurantSearchService restaurantSearchService;

    @GetMapping("/search/result")
    public String searchResult(@RequestParam(required = false) String restaurantKeyword, Model model) {
        if (restaurantKeyword != null) {
            List<SearchRestaurantResponseDto> results = restaurantSearchService.searchByName(restaurantKeyword);
            model.addAttribute("keyword", restaurantKeyword);
            model.addAttribute("results", results);
        }
        return "restaurant/search";
    }


    @GetMapping("search/detail/{id}")
    public String restaurantDetail(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantSearchService.findById(id);
        model.addAttribute("restaurant", restaurant);
        return "restaurant/detail";
    }
}
