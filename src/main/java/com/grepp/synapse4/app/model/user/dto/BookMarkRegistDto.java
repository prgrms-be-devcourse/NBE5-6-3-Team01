package com.grepp.synapse4.app.model.user.dto;

import com.grepp.synapse4.app.model.user.entity.Bookmark;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class BookMarkRegistDto {

    private final Long restaurantId;
    private final String name;
    private final String address;
    private final String branch;
    private final String category;
    private final LocalDateTime createdAt;

    public BookMarkRegistDto(Long restaurantId, String name, String address, String branch, String category, LocalDateTime createdAt) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.branch = branch;
        this.category = category;
        this.createdAt = createdAt;
    }

    public static BookMarkRegistDto fromEntity(Bookmark b) {
        return new BookMarkRegistDto(
                b.getRestaurant().getId(),
                b.getRestaurant().getName(),
                b.getRestaurant().getAddress(),
                b.getRestaurant().getBranch(),
                b.getRestaurant().getCategory(),
                b.getCreatedAt()
        );
    }
}
