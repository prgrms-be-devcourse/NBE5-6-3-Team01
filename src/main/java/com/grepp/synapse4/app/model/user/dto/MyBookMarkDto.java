package com.grepp.synapse4.app.model.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class MyBookMarkDto {
    private final Long        userId;
    private final Long        bookMarkId;
    private final LocalDateTime createdAt;
    private final Long        restaurantId;
    private final String      restaurantName;
    private final String      restaurantAddress;
    private final String      branch;
    private final String      category;
    private final String      businessTime;

    public MyBookMarkDto(Long userId, Long bookMarkId, LocalDateTime createdAt, Long restaurantId, String restaurantName, String restaurantAddress, String branch, String category, String businessTime) {
        this.userId          = userId;
        this.bookMarkId      = bookMarkId;
        this.createdAt       = createdAt;
        this.restaurantId    = restaurantId;
        this.restaurantName  = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.branch          = branch;
        this.category        = category;
        this.businessTime    = businessTime;
    }
}

