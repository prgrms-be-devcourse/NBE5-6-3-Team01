package com.grepp.synapse4.app.model.restaurant.dto.search;

import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchRestaurantResponseDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;

    private String category;
    private String roadAddress;
    private String businessTime;
    private String dayOff;
    private String tel;

    // todo menu db 붙이고 엮기
//    private List<MenuDto> menus;

    public static SearchRestaurantResponseDto fromEntity(Restaurant restaurant) {
        return new SearchRestaurantResponseDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getLatitude(),
                restaurant.getLongitude(),
                restaurant.getCategory(),
                restaurant.getRoadAddress(),
                restaurant.getDetail().getRowBusinessTime(),
                restaurant.getDetail().getDayOff(),
                restaurant.getDetail().getTel()
        );
    }

    public static class MenuDto{
        private Long id;
        private String name;
        private String imageUrl;
    }

}
