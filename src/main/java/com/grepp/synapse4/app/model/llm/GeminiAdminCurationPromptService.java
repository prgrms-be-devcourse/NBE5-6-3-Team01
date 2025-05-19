package com.grepp.synapse4.app.model.llm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepp.synapse4.app.model.llm.dto.userrecommenddto.GeminiFullResponseDto;
import com.grepp.synapse4.app.model.llm.dto.userrecommenddto.GeminiResponseDto;
import com.grepp.synapse4.app.model.llm.entity.Curation;
import com.grepp.synapse4.app.model.llm.entity.CurationResult;
import com.grepp.synapse4.app.model.llm.entity.LLMQuestion;
import com.grepp.synapse4.app.model.llm.entity.LLMResult;
import com.grepp.synapse4.app.model.llm.mongo.RestaurantTagsDocument;
import com.grepp.synapse4.app.model.llm.repository.CurationRepository;
import com.grepp.synapse4.app.model.llm.repository.CurationResultRepository;
import com.grepp.synapse4.app.model.llm.repository.RestaurantTagsDocumentRepository;
import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.app.model.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiAdminCurationPromptService {

    // 제미나이에게 보낼 프롬프트 제작 담당 서비스

    private final GeminiService geminiService;
    private final RestaurantTagsDocumentRepository restaurantTagsDocumentRepository;

    private final CurationRepository curationRepository;
    private final RestaurantRepository restaurantRepository;
    private final CurationResultRepository curationResultRepository;

    @Transactional
    public GeminiResponseDto generateRecommendations(Long id, String curationTitle) {
        // 1. text로 문자열 prompt 생성
        String prompt = buildAdminCurationPrompt(curationTitle);

        // 2. 완성된 requestDto를 GeminiService로 호출하여 String 형태로 받음
        String geminiResponse = geminiService.getGeminiResponse(prompt);
        System.out.println("🤖 gemini response: " + geminiResponse);

        // 3. 응답 파싱
        GeminiResponseDto responseDto = parseGeminiResponse(geminiResponse);

//         4. 응답 저장! 구조 다시 짜야함..
        saveResults(id, responseDto);

        return responseDto;
    }

    // 4. 도착한 응답 result DB 에 저장
    @Transactional
    public void saveResults(Long curationId, GeminiResponseDto responseDto) {
        Curation curation = curationRepository.findById(curationId)
                .orElseThrow(() -> new RuntimeException("큐레이션 없음"));

        List<CurationResult> results = new ArrayList<>();

        for (GeminiResponseDto.Recommendation rec : responseDto.getRecommendations()) {
            Restaurant restaurant = restaurantRepository.findById(rec.getRestaurantId())
                    .orElseThrow(() -> new RuntimeException("식당 없음"));

            results.add(new CurationResult(rec.getReason(), curation, restaurant));
        }

        curationResultRepository.saveAll(results);
    }


    // 3. 도착한 응답 파싱
    private GeminiResponseDto parseGeminiResponse(String geminiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // full response dto 파싱
            GeminiFullResponseDto fullDto = mapper.readValue(geminiResponse, GeminiFullResponseDto.class);

            // String json get
            String rawJson = fullDto.getCandidates()
                    .get(0)
                    .getContent()
                    .getParts()
                    .get(0)
                    .getText();

            // 영원히 돌아오는 백틱 제거
            String cleanedJson = rawJson.replaceAll("```json", "")
                    .replaceAll("```", "").trim();

            System.out.println("🤖 백틱 제거 결과: " + cleanedJson);

            // 꺼내온 값 response dto 형태로 파싱
            GeminiResponseDto responseDto = mapper.readValue(cleanedJson, GeminiResponseDto.class);

            return responseDto;
        } catch (Exception e) {
            throw new RuntimeException("❗ Gemini 응답 파싱 실패: " + e.getMessage(), e);
        }
    }


    //1. text로 문자열 prompt 생성
    private String buildAdminCurationPrompt(String userText) {
        StringBuilder tagBuilder = new StringBuilder();
        // todo 지금은 mongo에 15개 식당만 넣어놔서 findAll로 다 갖고 옴
        // 추후 태깅된 식당 모두 추가 시 findAll 로직을 따로 아래 메서드로 빼서 비즈니스 로직을 제작해야!
        for (RestaurantTagsDocument doc : restaurantTagsDocumentRepository.findAll()) {
            tagBuilder.append("식당 ID ").append(doc.getRestaurantId()).append(": ");
            tagBuilder.append(String.join(", ", doc.getTags())).append("\n");
        }

        return """
                다음은 큐레이션 주제명과 식당별 태그 정보입니다.

                [사용자 입력]
                %s

                [식당 태그 목록]
                %s

                위 정보를 참고하여, 주제에 어울리는 식당을 최대 3개 추천해 주세요.
                결과는 아래 Json 형식 그대로만 반환해 주세요
                백틱은 절대절대절대 포함하지 말아주세요.


                {
                  "recommendations": [
                    { "restaurantId": 7, "reason": "혼밥하기 좋고 조용한 분위기" },
                    { "restaurantId": 14, "reason": "프라이빗하고 고급스러운 분위기" }
                  ]
                }
                """.formatted(userText, tagBuilder.toString());
    }

}