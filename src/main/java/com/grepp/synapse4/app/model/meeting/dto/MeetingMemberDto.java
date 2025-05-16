package com.grepp.synapse4.app.model.meeting.dto;

import com.grepp.synapse4.app.model.meeting.code.State;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@Getter @Setter @ToString
public class MeetingMemberDto {
  private Long id;
  private Long meetingId;
  private Long userId;
  private State state;
  private LocalDateTime createdAt;
  private LocalDateTime deletedAt;
}

