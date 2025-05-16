package com.grepp.synapse4.app.model.llm.dto;

import com.grepp.synapse4.app.model.llm.entity.Curation;
import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString

public class CurationResultDto {

    private Long id;
    private String title;
    private String name;
    private String address;
    private boolean active;

    public CurationResultDto(Long id,String title, String name, String address, Boolean active) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.address = address;
        this.active = active;
    }



}