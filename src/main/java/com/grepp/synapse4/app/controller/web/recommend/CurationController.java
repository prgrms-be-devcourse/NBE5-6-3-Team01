package com.grepp.synapse4.app.controller.web.recommend;

import com.grepp.synapse4.app.model.llm.CurationService;
import com.grepp.synapse4.app.model.llm.dto.UserCurationDto;
import com.grepp.synapse4.app.model.llm.dto.UserCurationSurveyDto;
import com.grepp.synapse4.app.model.llm.dto.UserCurationSurveyDto.RestaurantDto;
import com.grepp.synapse4.app.model.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CurationController {

    private final CurationService curationService;

    @GetMapping("/curation")
    public String curationList(Model model,
                               @AuthenticationPrincipal UserDetails userDetails) {

        UserCurationSurveyDto surveys;

        if (userDetails != null) {
            // 로그인 유저: SurveyDto 리스트 그대로
            Long userId = ((CustomUserDetails) userDetails).getUser().getId();
            surveys = curationService.recommendCurationSurveys(userId);

        } else {
            // 비로그인 유저: 기존 UserCurationDto → SurveyDto 로 변환
            UserCurationDto publicDto = curationService.getLatestCurationRestaurants();

            List<RestaurantDto> restaurants = publicDto.getRestaurants().stream()
                    .map(cr -> new RestaurantDto(
                            cr.getId(),
                            cr.getName(),
                            cr.getCategory(),
                            cr.getRoadAddress(),
                            cr.getBranch(),
                            cr.getReason(),
                            cr.getBusinessTime()
                    ))
                    .collect(Collectors.toList());

            surveys = (new UserCurationSurveyDto(
                    publicDto.getId(),
                    publicDto.getTitle(),
                    restaurants
            ));
        }

        model.addAttribute("curation", surveys);
        return "recommend/user-curation";
    }
}

