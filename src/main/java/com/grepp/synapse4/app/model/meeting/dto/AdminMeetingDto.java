package com.grepp.synapse4.app.model.meeting.dto;

import com.grepp.synapse4.app.model.meeting.code.Purpose;
import com.grepp.synapse4.app.model.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString

public class AdminMeetingDto {

    private Long id;
    private String title;
    private User user;
    private String description;

    public AdminMeetingDto(Long id, String title, User user, String description) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.description = description;
    }


}
