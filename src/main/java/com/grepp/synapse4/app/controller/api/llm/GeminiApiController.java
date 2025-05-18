package com.grepp.synapse4.app.controller.api.llm;

import com.grepp.synapse4.app.model.llm.GeminiPromptService;
import com.grepp.synapse4.app.model.llm.dto.GeminiPromptDto;
import com.grepp.synapse4.app.model.llm.dto.GeminiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("recommend")
public class GeminiApiController {

    private final GeminiPromptService geminiPromptService;

    @PostMapping
    public ResponseEntity<?> getRecommend(@Valid @RequestBody GeminiPromptDto request,
                                               BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest()
                    .body(bindingResult.getFieldError().getDefaultMessage());
        }

        GeminiResponseDto result = geminiPromptService.generateRecommendations(request);
        return ResponseEntity.ok(result);
    }
}
