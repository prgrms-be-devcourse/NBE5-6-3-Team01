package com.grepp.synapse4.app.model.llm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepp.synapse4.app.model.llm.dto.GeminiFullResponseDto;
import com.grepp.synapse4.app.model.llm.dto.GeminiPromptDto;
import com.grepp.synapse4.app.model.llm.dto.GeminiResponseDto;
import com.grepp.synapse4.app.model.llm.mongo.RestaurantTagsDocument;
import com.grepp.synapse4.app.model.llm.repository.RestaurantTagsDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiPromptService {

    // 제미나이에게 보낼 프롬프트 제작 담당 서비스

    private final GeminiService geminiService;
    private final RestaurantTagsDocumentRepository restaurantTagsDocumentRepository;

    public GeminiResponseDto generateRecommendations(GeminiPromptDto inputDto) {
        // 1. 프롬프트 문자열 생성
        String prompt = buildPrompt(inputDto);

        // 2. Gemini API에 요청
        // prompt를 담아서 Response로 보내서, GeminiRequestDto를 생성
        // 그러면 알아서 GeminiService에서 post해줌
        String geminiResponse = geminiService.getGeminiResponse(prompt);

        System.out.println("🤖 gemini response: " + geminiResponse);

        // 3. post 이후 도착한 응답 파싱
        return parseGeminiResponse(geminiResponse);
    }

    private String buildPrompt(GeminiPromptDto dto) {
        StringBuilder tagBuilder = new StringBuilder();
        for (RestaurantTagsDocument doc : restaurantTagsDocumentRepository.findAll()) {
            tagBuilder.append("식당 ID ").append(doc.getRestaurantId()).append(": ");
            tagBuilder.append(String.join(", ", doc.getTags())).append("\n");
        }

        return """
        다음은 사용자의 입력 문장과 식당별 태그 정보입니다.

        [사용자 입력]
        %s

        [식당 태그 목록]
        %s

        위 정보를 참고하여, 사용자의 의도에 맞는 식당을 최대 3개 추천해 주세요.
        결과는 아래 Json 형식 그대로만 반환해 주세요
        백틱은 포함하지 말아주세요.
        
        
        {
          "recommendations": [
            { "restaurantId": 7, "reason": "혼밥하기 좋고 조용한 분위기" },
            { "restaurantId": 14, "reason": "프라이빗하고 고급스러운 분위기" }
          ]
        }
        """.formatted(dto.getUserText(), tagBuilder.toString());
    }

    // gemini -> java 파싱
    private GeminiResponseDto parseGeminiResponse(String responseText) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // full response dto 파싱
            GeminiFullResponseDto fullDto = mapper.readValue(responseText, GeminiFullResponseDto.class);

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
}
