package com.grepp.synapse4.app.model.user;


import com.grepp.synapse4.app.model.user.entity.Bookmark;
import com.grepp.synapse4.app.model.user.repository.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    private final BookMarkRepository bookMarkRepository;

    public List<Bookmark> findByUserId(Long userId) {
        return bookMarkRepository.findByUserId(userId);
    }






}
