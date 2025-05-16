package com.grepp.synapse4.app.model.restaurant;

import com.grepp.synapse4.app.model.restaurant.dto.search.SearchRestaurantResponseDto;
import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.app.model.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantSearchService {

    private final RestaurantRepository restaurantRepository;

    public List<SearchRestaurantResponseDto> searchByName(String restaurantKeyword){
        if (restaurantKeyword == null || restaurantKeyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어를 입력하세요");
        }
        List<Restaurant> restaurants = restaurantRepository.findByNameContainingAndActivatedIsTrue(restaurantKeyword);
        return restaurants.stream()
                .map(restaurant -> SearchRestaurantResponseDto.fromEntity(restaurant))
                .collect(Collectors.toList());
    }
}
