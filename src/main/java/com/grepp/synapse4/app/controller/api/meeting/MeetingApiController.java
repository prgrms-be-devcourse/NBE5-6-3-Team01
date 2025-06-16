package com.grepp.synapse4.app.controller.api.meeting;

import com.grepp.synapse4.app.controller.web.meeting.payload.meeting.MeetingAlarmRequest;
import com.grepp.synapse4.app.controller.web.meeting.payload.meeting.MeetingInviteRequest;
import com.grepp.synapse4.app.model.meeting.MeetingService;
import com.grepp.synapse4.app.model.meeting.code.State;
import com.grepp.synapse4.app.model.meeting.dto.MeetingMemberDto
import com.grepp.synapse4.app.model.meeting.entity.Meeting;
import com.grepp.synapse4.app.model.notification.NotificationService;
import com.grepp.synapse4.app.model.notification.code.NotificationType;
import com.grepp.synapse4.app.model.notification.dto.NotificationDto;
import com.grepp.synapse4.app.model.user.CustomUserDetailsService;
import com.grepp.synapse4.app.model.user.dto.CustomUserDetails;
import com.grepp.synapse4.infra.response.ApiResponse;
import com.grepp.synapse4.infra.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/meeting")
@RequiredArgsConstructor
@Slf4j
public class MeetingApiController {

    private final MeetingService meetingService;
    private final NotificationService notificationService;
    private final CustomUserDetailsService customUserDetailsService;

    // 모임 초대
    @PostMapping("/invite/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> invite(
        @RequestBody MeetingInviteRequest invite
    ){
        Long meetingId = invite.getMeetingId();
        String account = invite.getAccount();

        Boolean existByUser = customUserDetailsService.findUserByAccount(account);
        if(!existByUser){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ResponseCode.USER_NOT_FOUND));
        }

        Long userId = customUserDetailsService.loadUserIdByAccount(account);
        Boolean existByMeetingMember = meetingService.findMemberByMeetingIdAndUserId(meetingId, userId);
        if(existByMeetingMember){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ResponseCode.USER_ALREADY_INVITED));
        }

        MeetingMemberDto memberDto = new MeetingMemberDto(meetingId, userId);
        Meeting meeting = meetingService.inviteUser(memberDto);

        NotificationDto notiDto = NotificationDto.builder()
                .userId(userId)
                .meeting(meeting)
                .type(NotificationType.MEETING)
                .redirectUrl("meetings/detail/"+meetingId)
                .build();

        notificationService.registNotification(notiDto);
        notificationService.sendNotification(userId, notiDto, NotificationType.MEETING);

        return ResponseEntity.ok().build();
    }

    // 모임 수락 or 거절
    @PatchMapping("/invite/change/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> handleInvite(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody MeetingAlarmRequest alarm
    ){
        Long meetingId = alarm.getMeetingId();
        Long userId = userDetails.getUser().getId();
        State state = alarm.getState();

        meetingService.updateInvitedState(meetingId, userId, state);

        return ResponseEntity.ok().build();
    }
}
