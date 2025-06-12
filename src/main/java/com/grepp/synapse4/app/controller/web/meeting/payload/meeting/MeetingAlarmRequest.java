package com.grepp.synapse4.app.controller.web.meeting.payload.meeting;

import com.grepp.synapse4.app.model.meeting.code.State;
import lombok.Getter;

@Getter
public class MeetingAlarmRequest {

    Long meetingId;
    State state;

}
