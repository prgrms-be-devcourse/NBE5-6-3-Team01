package com.grepp.synapse4.app.model.user.repository;

import com.grepp.synapse4.app.model.user.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> findByUserId(Long userId);

}
