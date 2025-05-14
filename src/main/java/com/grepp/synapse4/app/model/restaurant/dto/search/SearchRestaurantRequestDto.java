package com.grepp.synapse4.app.model.restaurant.dto.search;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRestaurantRequestDto {

    @NotBlank
    private String restaurantKeyword;
}
