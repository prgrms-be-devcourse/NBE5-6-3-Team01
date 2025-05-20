package com.grepp.synapse4.app.controller.api.llm;

import com.grepp.synapse4.app.model.llm.GeminiUserRecommendPromptService;
import com.grepp.synapse4.app.model.llm.LlmQuestionService;
import com.grepp.synapse4.app.model.llm.dto.userrecommenddto.GeminiResponseDto;
import com.grepp.synapse4.app.model.llm.dto.userrecommenddto.RecommendRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("recommend")
public class RecommendApiController {

    private final LlmQuestionService llmQuestionService;
    private final GeminiUserRecommendPromptService geminiUserRecommendPromptService;

    @PostMapping
    public ResponseEntity<GeminiResponseDto> recommendForUserQuestion(@RequestBody RecommendRequestDto dto){
        // 1. 사용자 입력 저장
        String llmQuestionId = llmQuestionService.saveQuestion(dto);

        // 2. gemini 호출
        GeminiResponseDto response = geminiUserRecommendPromptService.generateRecommendations(llmQuestionId);

        return ResponseEntity.ok(response);
    }
}
