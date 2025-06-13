package com.grepp.synapse4.app.model.meeting.entity;

import com.grepp.synapse4.app.model.meeting.code.State;
import com.grepp.synapse4.app.model.user.entity.User;
import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Getter @Setter
public class MeetingMember extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "MeetingMember{" +
            "id=" + id +
            ", state=" + state +
            ", meetingId=" + (meeting != null ? meeting.getId() : "null") +
            ", userId=" + (user != null ? user.getId() : "null") +
            '}';
    }
}