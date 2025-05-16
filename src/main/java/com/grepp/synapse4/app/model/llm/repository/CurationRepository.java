package com.grepp.synapse4.app.model.llm.repository;

import com.grepp.synapse4.app.model.llm.entity.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurationRepository extends JpaRepository<Curation, Long> {


    Curation findTopByOrderByCreatedAtDesc();
}
