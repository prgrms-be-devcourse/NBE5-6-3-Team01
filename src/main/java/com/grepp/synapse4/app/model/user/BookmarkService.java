package com.grepp.synapse4.app.model.user;


import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.app.model.restaurant.repository.RestaurantRepository;
import com.grepp.synapse4.app.model.user.dto.BookMarkDto;
import com.grepp.synapse4.app.model.user.dto.BookMarkRegistDto;
import com.grepp.synapse4.app.model.user.dto.MyBookMarkDto;
import com.grepp.synapse4.app.model.user.entity.Bookmark;
import com.grepp.synapse4.app.model.user.entity.User;
import com.grepp.synapse4.app.model.user.repository.BookMarkRepository;
import com.grepp.synapse4.app.model.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookMarkRepository bookMarkRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public List<Bookmark> findByUserId(Long userId) {
        return bookMarkRepository.findByUserId(userId);
    }

    public List<MyBookMarkDto> findByBookmarkId(Long userId) throws NotFoundException {
        List<MyBookMarkDto> mybookMarkDtos = bookMarkRepository.findmybookmark(userId);
        return bookMarkRepository.findmybookmark(userId);
    }

    public List<BookMarkDto> getUserBookmarks(Long userId) {
        return bookMarkRepository.findByUserId(userId).stream()
                .map(BookMarkDto::fromEntity)
                .toList();
    }

    public boolean toggleBookmark(Long userId, Long restaurantId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UsernameNotFoundException("유저 없음: " + userId);
        }
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new IllegalArgumentException("식당 없음: " + restaurantId);
        }
        boolean exists = bookMarkRepository
                .findByUserIdAndRestaurantId(userId, restaurantId)
                .isPresent();
        if (exists) {
            bookMarkRepository.deleteByUserIdAndRestaurantId(userId, restaurantId);
            return false;
        } else {
            bookMarkRepository.insertBookmark(userId, restaurantId);
            return true;
        }
    }
}