package com.grepp.synapse4.app.controller.api;

import com.grepp.synapse4.app.model.user.BookmarkService;
import com.grepp.synapse4.app.model.user.SurveyService;
import com.grepp.synapse4.app.model.user.dto.BookMarkDto;
import com.grepp.synapse4.app.model.user.dto.SurveyDto;
import com.grepp.synapse4.app.model.user.entity.Bookmark;
import com.grepp.synapse4.app.model.user.entity.Survey;
import com.grepp.synapse4.infra.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminApiController {

    private final SurveyService surveyService;
    private final BookmarkService bookmarkService;

    // TODO : survey 엔티티변경(이넘값)으로인해 변경해야함
    @GetMapping("/users/prefer/{userId}")
    public ResponseEntity<ApiResponse<List<SurveyDto>>> getUserPrefer(
            @PathVariable("userId") Long userId) {
        List<Survey> surveys = surveyService.findByUserId(userId);
        List<SurveyDto> dtoList = surveys.stream()
                .map(SurveyDto::fromEntity)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }

    @GetMapping("/users/bookmark/{userId}")
    public ResponseEntity<ApiResponse<List<BookMarkDto>>> getUserBookmark(
            @PathVariable("userId") Long userId) {
        List<Bookmark> entities = bookmarkService.findByUserId(userId);
        List<BookMarkDto> dtoList = entities.stream()
                .map(BookMarkDto::fromEntity)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }

}
