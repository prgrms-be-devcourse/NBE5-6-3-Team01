package com.grepp.synapse4.app.model.llm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeminiPromptDto {

    // admin이 입력한 주제, 사용자가 입력하는 상황 입력 문장을
    // 제미나이에게 각각 전달하기 위한 공통 dto

    @NotBlank(message = "입력값은 필수입니다.")
    private String userText;
    private Long curationId;    // admin이 입력한 주제일 경우만 사용
    private Long llmQuestionId; // user가 입력한 상황 검색일 경우만 사용
    private String inputActor;
}
