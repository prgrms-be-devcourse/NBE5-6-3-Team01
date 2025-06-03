package com.grepp.synapse4.app.controller.api;

import com.grepp.synapse4.app.model.meeting.MeetingService;
import com.grepp.synapse4.app.model.meeting.dto.AdminMeetingMemberDto;
import com.grepp.synapse4.app.model.meeting.dto.MeetingMemberDto;
import com.grepp.synapse4.app.model.user.BookmarkService;
import com.grepp.synapse4.app.model.user.PreferService;
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

    private final BookmarkService bookmarkService;
    private final PreferService preferService;
    private final MeetingService meetingService;

    @GetMapping("/users/prefer/{userId}")
    public ResponseEntity<ApiResponse<List<SurveyDto>>> getUserPrefer(
            @PathVariable Long userId) {
        List<SurveyDto> dtoList = preferService.getUserPreferences(userId);
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }

    @GetMapping("/users/bookmark/{userId}")
    public ResponseEntity<ApiResponse<List<BookMarkDto>>> getUserBookmark(
            @PathVariable("userId") Long userId) {
        List<BookMarkDto> dtoList = bookmarkService.getUserBookmarks(userId);
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }

    @GetMapping("meetingmember/{meetingId}")
    public ResponseEntity<ApiResponse<List<AdminMeetingMemberDto>>> getMeetingMember(
            @PathVariable Long meetingId){
        List<AdminMeetingMemberDto> dtos = meetingService.findAdminMeetingByUserNickname(meetingId);
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

}
