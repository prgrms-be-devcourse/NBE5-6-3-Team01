package com.grepp.synapse4.app.controller.api.restaurant;

import com.grepp.synapse4.app.model.restaurant.RestaurantSearchService;
import com.grepp.synapse4.app.model.restaurant.dto.search.SearchRestaurantRequestDto;
import com.grepp.synapse4.app.model.restaurant.dto.search.SearchRestaurantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class MainSearchApiController {

    // todo result view fetch 처리... 하고 싶다.
    private final RestaurantSearchService restaurantSearchService;

    @GetMapping("/api/search")
    public ResponseEntity<List<SearchRestaurantResponseDto>> searchRestaurants(
            @ModelAttribute SearchRestaurantRequestDto requestDto) {

        List<SearchRestaurantResponseDto> respon = restaurantSearchService.searchByName(requestDto.getRestaurantKeyword());
        return ResponseEntity.ok(respon);
    }

}
