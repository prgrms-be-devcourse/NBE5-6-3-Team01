package com.grepp.synapse4.app.model.llm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CurationDto {

    private Long id;
    private String title;

    List<CurationRestaurantDto> restaurants;

}
