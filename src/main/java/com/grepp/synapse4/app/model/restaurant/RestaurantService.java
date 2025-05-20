package com.grepp.synapse4.app.model.restaurant;

import com.grepp.synapse4.app.model.restaurant.dto.RestaurantDto;
import com.grepp.synapse4.app.model.restaurant.repository.RestaurantRepository;
import com.grepp.synapse4.app.model.user.dto.RankingDto;
import com.grepp.synapse4.app.model.user.repository.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final BookMarkRepository bookMarkRepository;
    private final RestaurantRepository restaurantRepository;

    public List<RankingDto> getRestaurantRanking() {
        return bookMarkRepository.findRestaurantRanking();
    }


    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findTop10ByActivatedIsTrueOrderByIdDesc().stream()
                .map(RestaurantDto::from)
                .toList();
    }
}
