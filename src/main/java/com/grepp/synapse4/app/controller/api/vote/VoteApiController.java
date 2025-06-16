package com.grepp.synapse4.app.controller.api.vote;

import com.grepp.synapse4.app.controller.web.meeting.payload.vote.VoteRequest;
import com.grepp.synapse4.app.model.meeting.VoteService;
import com.grepp.synapse4.app.model.notification.NotificationService;
import com.grepp.synapse4.app.model.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/vote")
@RequiredArgsConstructor
@Slf4j
public class VoteApiController {

    private final VoteService voteService;
    private final NotificationService notificationService;

    @PatchMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> vote(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody VoteRequest request
    ){
        Long userId = userDetails.getUser().getId();
        voteService.vote(request.getVoteId(), userId, request.getIsJoined());
        notificationService.removeVoteNotification(userId, request.getVoteId());

        return ResponseEntity.ok().build();
    }
}
