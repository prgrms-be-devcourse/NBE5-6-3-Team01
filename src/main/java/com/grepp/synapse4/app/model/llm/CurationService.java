package com.grepp.synapse4.app.model.llm;

import com.grepp.synapse4.app.model.llm.dto.AdminCurationDto;
import com.grepp.synapse4.app.model.llm.dto.CurationRestaurantDto;
import com.grepp.synapse4.app.model.llm.dto.UserCurationDto;
import com.grepp.synapse4.app.model.llm.dto.UserCurationSurveyDto;
import com.grepp.synapse4.app.model.llm.entity.Curation;
import com.grepp.synapse4.app.model.llm.repository.CurationRepository;
import com.grepp.synapse4.app.model.llm.repository.CurationResultRepository;
import com.grepp.synapse4.app.model.user.entity.Survey;
import com.grepp.synapse4.app.model.user.repository.SurveyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurationService {

    private final CurationRepository curationRepository;
    private final GeminiAdminCurationPromptService geminiAdminCurationPromptService;
    private final SurveyRepository surveyRepository;

    @Transactional
    public void setCuration(AdminCurationDto dto) {

        // 1. 큐레이션 엔티티 저장
        Curation curation = new Curation();
        curation.setTitle(dto.getTitle());
        curation.setCompanyLocation(String.valueOf(dto.getCompanyLocation()));
        curation.setCompanion(String.valueOf(dto.getCompanion()));
        curation.setPurpose(String.valueOf(dto.getPurpose()));
        curation.setFavoriteCategory(String.valueOf(dto.getFavoriteCategory()));
        curation.setPreferredMood(String.valueOf(dto.getPreferredMood()));

        Curation savedCuration = curationRepository.save(curation);
        Long id = savedCuration.getId();
        String curationTitle = savedCuration.getTitle();

        // 2. Gemini 호출
        geminiAdminCurationPromptService.generateRecommendations(id, curationTitle);

    }


    //     우선 가장 최신 등록한 큐레이션만 노출하도록 로직 설계
    //     4개 테이블 조인해서 lazy 이슈로 서비스단에서 트랜젝션 리드온니처리
    @Transactional(readOnly = true)
    public UserCurationDto getLatestCurationRestaurants() {

        // 1. 최신 큐레이션 엔티티
        Curation latest = curationRepository.findTopByOrderByCreatedAtDesc();

        if (ObjectUtils.isEmpty(latest)) {
            return null;
        }

        if (latest.getResults() == null || latest.getResults().isEmpty()) {
            return null;
        }


        // 2. 최신 큐레이션 엔티티의 결과 리스트 엔티티
        // 3. 큐레이션 결과의 1개 식당과 매핑
        //    그렇게 매핑된 식당을 '큐레이션식당 dto'에 담기
        List<CurationRestaurantDto> dtos = latest.getResults().stream()
                .map(result -> CurationRestaurantDto.fromEntity(result.getRestaurant(), result))
                .toList();

        return new UserCurationDto(
                latest.getId(),
                latest.getTitle(),
                dtos);
    }


    public List<UserCurationSurveyDto> recommendCurationSurveys(Long userId) {
        // 사용자 설문 조회
        Survey survey = surveyRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당유저의 설문조사가 없습니다."));

        String loc = survey.getCompanyLocation();
        String companion = survey.getCompanion();
        String purpose = survey.getPurpose();
        String favoriteCategory = survey.getFavoriteCategory();
        String preferredMood = survey.getPreferredMood();

        // 모든 큐레이션 조회
        List<Curation> allCurations = curationRepository.findCurationIdWithRestaurantId();

        // 매칭 점수 계산 및 최고점 큐레이션 선택
        Curation best = allCurations.stream()
                .map(c -> new MatchPair(
                        c,
                        calcScore(
                                loc, purpose, companion, favoriteCategory, preferredMood,
                                c.getCompanyLocation(),
                                c.getPurpose(),
                                c.getCompanion(),
                                c.getFavoriteCategory(),
                                c.getPreferredMood()
                        )
                ))
                .max(Comparator.comparingInt(MatchPair::getScore))
                .orElseThrow(() -> new RuntimeException("적합한 큐레이션이 없습니다."))
                .getCuration();

        // 최고 점수 Curation의 식당 리스트 DTO 생성 (최대 3개)
        List<UserCurationSurveyDto.RestaurantDto> restaurants = best.getResults().stream()
                // CurationResult → CurationRestaurantDto
                .map(cr -> CurationRestaurantDto.fromEntity(cr.getRestaurant(), cr))
                // CurationRestaurantDto → SurveyDto.RestaurantDto
                .map(dto -> new UserCurationSurveyDto.RestaurantDto(
                        dto.getId(),
                        dto.getName(),
                        dto.getCategory(),
                        dto.getRoadAddress(),
                        dto.getBranch(),
                        dto.getReason(),
                        dto.getBusinessTime()
                ))
                .limit(3)
                .toList();

        // 단일 Survey DTO 반환
        return Collections.singletonList(new UserCurationSurveyDto(
                best.getId(),
                best.getTitle(),
                restaurants
        ));
    }

    // 점수 계산기
    private int calcScore(
            String uLoc, String uPurpose, String uCompanion, String uCategory, String uMood,
            String cLoc, String cPurpose, String cCompanion, String cCategory, String cMood
    ) {
        int score = 0;
        if (uLoc.equalsIgnoreCase(cLoc)) score += 3;
        if (uPurpose.equalsIgnoreCase(cPurpose)) score += 2;
        if (uCompanion.equalsIgnoreCase(cCompanion)) score += 2;
        if (uCategory.equalsIgnoreCase(cCategory)) score += 1;
        if (uMood.equalsIgnoreCase(cMood)) score += 1;
        return score;
    }

    @Getter
    private static class MatchPair {
        private final Curation curation;
        private final int score;

        public MatchPair(Curation curation, int score) {
            this.curation = curation;
            this.score = score;
        }
    }
}