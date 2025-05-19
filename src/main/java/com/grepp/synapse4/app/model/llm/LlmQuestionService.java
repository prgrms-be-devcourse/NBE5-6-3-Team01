package com.grepp.synapse4.app.model.llm;

import com.grepp.synapse4.app.model.llm.dto.userrecommenddto.RecommendRequestDto;
import com.grepp.synapse4.app.model.llm.entity.LLMQuestion;
import com.grepp.synapse4.app.model.llm.repository.LlmQuestionRepository;
import com.grepp.synapse4.app.model.user.entity.User;
import com.grepp.synapse4.app.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LlmQuestionService {

    private final LlmQuestionRepository llmQuestionRepository;
    private final UserRepository userRepository;

    public Long saveQuestion(RecommendRequestDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유효한 유저가 없음"));

        LLMQuestion question = LLMQuestion.builder()
                .user(user)
                .text(dto.getQuestionText())
                .build();
        LLMQuestion saved = llmQuestionRepository.save(question);

        return saved.getId();
    }
}
