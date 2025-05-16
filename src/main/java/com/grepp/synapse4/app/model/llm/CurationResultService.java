package com.grepp.synapse4.app.model.llm;

import com.grepp.synapse4.app.model.llm.dto.CurationResultDto;
import com.grepp.synapse4.app.model.llm.entity.CurationResult;
import com.grepp.synapse4.app.model.llm.repository.CurationResultRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CurationResultService {

    private final CurationResultRepository repository;
//    private EntityManager entityManager;

    public List<CurationResultDto> getResultsByCurationId() {
        return repository.findResultsByCurationId();
    }

//    // Dirty-Checking 방식
//    public void toggleActive(Long id) {
//        CurationResult cr = repository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("CurationResult not found: " + id));
//        cr.setActive(!cr.isActive());
//        repository.flush();
//        entityManager.clear();
//    }

    // 쿼리 modify 방식
    @Transactional
    public void toggleActive(Long id) {
        int updated = repository.toggleActiveById(id);
        if (updated == 0) {
            throw new EntityNotFoundException("큐레이션 결과가 없습니다.");
        }
    }
}




