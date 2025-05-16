package com.grepp.synapse4.app.model.user.dto;

import com.grepp.synapse4.app.model.user.entity.Survey;
import lombok.*;

@Getter @Setter @ToString

public class SurveyDto {

    private Long surveyId;
    private String companyLocation;
    private String purpose;
    private String companion;
    private String favoriteCategory;
    private String preferMood;
    private Long userId;

    public static SurveyDto fromEntity(Survey s) {
        SurveyDto dto = new SurveyDto();
        dto.setSurveyId(s.getId());
        dto.setCompanyLocation(s.getCompanyLocation());
        dto.setPurpose(s.getPurpose());
        dto.setCompanion(s.getCompanion());
        dto.setFavoriteCategory(String.valueOf(s.getFavoriteCategory()));
        dto.setPreferMood(s.getPreferredMood());
        dto.setUserId(s.getUser().getId());
        return dto;
    }


}
