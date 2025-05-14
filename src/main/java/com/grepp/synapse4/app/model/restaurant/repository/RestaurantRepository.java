package com.grepp.synapse4.app.model.restaurant.repository;

import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // todo 중복 데이터면 init 처리 x
    boolean existsByNameAndLatitudeAndLongitude(String name, Double latitude, Double longitude);

    List<Restaurant> findByActivatedFalse();

    List<Restaurant> findByNameContainingAndActivatedIsTrue(String restaurantKeyword);
}
