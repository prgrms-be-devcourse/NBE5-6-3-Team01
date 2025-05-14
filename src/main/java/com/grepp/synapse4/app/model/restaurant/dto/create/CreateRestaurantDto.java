package com.grepp.synapse4.app.model.restaurant.dto.create;

import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.app.model.restaurant.entity.RestaurantDetail;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CreateRestaurantDto {

    // 기본정보 - restaurant
    @CsvBindByName(column = "식당명")
    private String name;
    @CsvBindByName(column = "지점명")
    private String branch;
    @CsvBindByName(column = "도로명주소")
    private String roadAddress;
    @CsvBindByName(column = "지번주소")
    private String jibunAddress;
    @CsvBindByName(column = "식당위도")
    private Double latitude;
    @CsvBindByName(column = "식당경도")
    private Double longitude;
    @CsvBindByName(column = "영업신고증업태명")
    private String category;

    // 상세 정보 - restaurantDetails
    private String dayOff;
    private String rowBusinessTime;
    @CsvBindByName(column = "식당대표전화번호")
    private String tel;
    private Boolean parking;
    private Boolean wifi;
    private Boolean delivery;
    private String homePageURL;

    private String publicId;
    private String kakaoId;


    private List<MenuDto> menus;

    private static class MenuDto {
        private String name;
        private Integer price;

        private List<String> imageURLs;
    }

    public Restaurant toEntity() {
        return Restaurant.builder()
                .publicId(this.publicId)
                .name(this.name)
                .branch(this.branch)
                .roadAddress(this.roadAddress)
                .jibunAddress(this.jibunAddress)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .category(this.category)
                .activated(false)
                .build();
    }

    public RestaurantDetail toDetailEntity(Restaurant restaurant) {
        return RestaurantDetail.builder()
                .restaurant(restaurant)
                .dayOff(this.dayOff)
                .rowBusinessTime(this.rowBusinessTime)
                .tel(this.tel)
                .parking(this.parking)
                .wifi(this.wifi)
                .delivery(this.delivery)
                .homePageURL(this.homePageURL)
                .build();
    }
}
