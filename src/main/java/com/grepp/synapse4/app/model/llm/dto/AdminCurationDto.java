package com.grepp.synapse4.app.model.llm.dto;

import com.grepp.synapse4.app.model.llm.code.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter @Setter @ToString
public class AdminCurationDto {

    private Long id;
    private String title;
    private CompanyLocation companyLocation;
    private Purpose purpose;
    private Companion companion;
    private FavoriteCategory favoriteCategory;
    private PreferredMood preferredMood;
    private Boolean activated;

}
