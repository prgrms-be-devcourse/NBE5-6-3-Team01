package com.grepp.synapse4.app.model.llm.repository;

import com.grepp.synapse4.app.model.llm.entity.LLMResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LlmResultRepository extends JpaRepository<LLMResult, Long> {
}
