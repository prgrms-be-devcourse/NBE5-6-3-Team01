package com.grepp.synapse4.app.model.llm;

import com.grepp.synapse4.app.model.llm.dto.AdminCurationDto;
import com.grepp.synapse4.app.model.llm.dto.CurationRestaurantDto;
import com.grepp.synapse4.app.model.llm.dto.UserCurationDto;
import com.grepp.synapse4.app.model.llm.entity.Curation;
import com.grepp.synapse4.app.model.llm.repository.CurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurationService {

    private final CurationRepository curationRepository;
    private final GeminiAdminCurationPromptService geminiAdminCurationPromptService;

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

        // 2. 최신 큐레이션 엔티티의 결과 리스트 엔티티
        // 3. 큐레이션 결과의 1개 식당과 매핑
        //    그렇게 매핑된 식당을 '큐레이션식당 dto'에 담기
        List<CurationRestaurantDto> dtos = latest.getResults().stream()
                .map(result -> result.getRestaurant())
                .map(restaurant -> CurationRestaurantDto.fromEntity(restaurant))
                .collect(Collectors.toList());

        return new UserCurationDto(
                latest.getId(),
                latest.getTitle(),
                dtos );
    }
}
