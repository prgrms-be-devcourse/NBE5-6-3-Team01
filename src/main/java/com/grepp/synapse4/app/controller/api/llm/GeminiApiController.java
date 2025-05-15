package com.grepp.synapse4.app.controller.api.llm;

import com.grepp.synapse4.app.model.llm.GeminiService;
import com.grepp.synapse4.app.model.llm.dto.GeminiRequestDto;
import com.grepp.synapse4.app.model.llm.dto.GeminiUserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("recommend")
public class GeminiApiController {

    private final GeminiService geminiService;

    @PostMapping
    public ResponseEntity<String> getRecommend(@RequestBody GeminiUserRequestDto request){
        String userText = request.getUserText();
        log.info("입력: {}", userText);

        String geminiResponse = geminiService.getGeminiResponse(userText);
        log.info("응답: {}", geminiResponse);

        return ResponseEntity.ok(geminiResponse);
    }
}
