package com.grepp.synapse4.app.controller.api.vote;

import com.grepp.synapse4.app.controller.web.meeting.payload.vote.VoteRegistRequest;
import com.grepp.synapse4.app.controller.web.meeting.payload.vote.VoteRequest;
import com.grepp.synapse4.app.model.meeting.VoteService;
import com.grepp.synapse4.app.model.meeting.entity.vote.Vote;
import com.grepp.synapse4.app.model.user.CustomUserDetailsService;
import com.grepp.synapse4.app.model.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/vote")
@RequiredArgsConstructor
@Slf4j
public class VoteApiController {

    private final VoteService voteService;

    @PatchMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> vote(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody VoteRequest request
    ){
        Long userId = userDetails.getUser().getId();
        voteService.vote(request.getVoteId(), userId, request.getIsJoined());

        return ResponseEntity.ok().build();
    }

    @PostMapping("regist/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> voteRegist(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody VoteRegistRequest registRequest
    ){

        return ResponseEntity.ok().build();
    }
}
