package com.grepp.synapse4.app.model.llm;

import com.grepp.synapse4.app.model.llm.dto.CurationDto;
import com.grepp.synapse4.app.model.llm.entity.Curation;
import com.grepp.synapse4.app.model.llm.repository.CurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurationService {

    private final CurationRepository curationRepository;

    @Transactional
    public void setCuration(CurationDto dto) {
        Curation curation = new Curation();
        curation.setTitle(dto.getTitle());
        curation.setCompanyLocation(String.valueOf(dto.getCompanyLocation()));
        curation.setCompanion(String.valueOf(dto.getCompanion()));
        curation.setPurpose(String.valueOf(dto.getPurpose()));
        curation.setFavoriteCategory(String.valueOf(dto.getFavoriteCategory()));
        curation.setPreferredMood(String.valueOf(dto.getPreferredMood()));
        curationRepository.save(curation);
    }

}
