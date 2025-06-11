package com.grepp.synapse4.app.controller.api.meeting;

import com.grepp.synapse4.app.controller.web.meeting.payload.meeting.MeetingInviteRequest;
import com.grepp.synapse4.app.model.meeting.MeetingService;
import com.grepp.synapse4.app.model.meeting.dto.MeetingMemberDto;
import com.grepp.synapse4.app.model.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/meeting")
@RequiredArgsConstructor
public class MeetingApiController {

    @Autowired
    private MeetingService meetingService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/invite/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> invite(
        @RequestBody MeetingInviteRequest invite
    ){
        Long meetingId = invite.getMeetingId();
        String account = invite.getAccount();

        Boolean existByUser = customUserDetailsService.findUserByAccount(account);
        if(!existByUser){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("존재하지 않는 유저입니다.");
        }

        Long userId = customUserDetailsService.loadUserIdByAccount(account);
        Boolean existByMeetingMember = meetingService.findMemberByMeetingIdAndUserId(meetingId, userId);
        if(existByMeetingMember){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("이미 초대된 유저입니다.");
        }

        MeetingMemberDto dto = new MeetingMemberDto(meetingId, userId);
        meetingService.inviteUser(dto);

        return ResponseEntity.ok().build();
    }

}
