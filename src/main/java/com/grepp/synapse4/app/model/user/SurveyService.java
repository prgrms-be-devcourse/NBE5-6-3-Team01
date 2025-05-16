package com.grepp.synapse4.app.model.user;

import com.grepp.synapse4.app.model.user.entity.Survey;
import com.grepp.synapse4.app.model.user.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public List<Survey> findByUserId(Long userId) {
        return surveyRepository.findByUserId(userId);
    }

}
