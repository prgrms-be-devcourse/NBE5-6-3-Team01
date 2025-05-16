package com.grepp.synapse4.app.controller.web.meeting.payload;

import com.grepp.synapse4.app.model.meeting.code.State;
import com.grepp.synapse4.app.model.meeting.dto.MeetingMemberDto;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MeetingInviteRequest {

  private Long meetingId;
  private Long userId;
  private State state = State.WAIT;
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime deletedAt = null;

  public MeetingMemberDto toDto(Long meetingId, Long userId){
    MeetingMemberDto meetingMemberDto = new MeetingMemberDto();

    meetingMemberDto.setMeetingId(meetingId);
    meetingMemberDto.setUserId(userId);
    meetingMemberDto.setState(state);
    meetingMemberDto.setCreatedAt(createdAt);

    return meetingMemberDto;
  }

}

