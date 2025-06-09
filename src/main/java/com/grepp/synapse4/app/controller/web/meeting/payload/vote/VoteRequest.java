package com.grepp.synapse4.app.controller.web.meeting.payload.vote;

import lombok.Data;

@Data
public class VoteRequest {
    private Long voteId;
    private Boolean isJoined;
}
