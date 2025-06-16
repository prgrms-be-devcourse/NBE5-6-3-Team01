package com.grepp.synapse4.app.model.notification.dto;

import com.grepp.synapse4.app.model.meeting.entity.Meeting;
import com.grepp.synapse4.app.model.meeting.entity.vote.Vote;
import com.grepp.synapse4.app.model.notification.code.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class NotificationDto {

    private Long userId;
    private Meeting meeting;
    private Vote vote;
    private NotificationType type;
    private String redirectUrl;

}
