package com.grepp.synapse4.app.model.meeting.dto;

import com.grepp.synapse4.app.model.meeting.code.Purpose;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString

public class AdminMeetingDto {

    private Long id;
    private String title;
    private String userAccount;
    private String description;

    public AdminMeetingDto(Long id, String title, String userAccount,String description) {
        this.id = id;
        this.title = title;
        this.userAccount = userAccount;
        this.description = description;
    }


}
