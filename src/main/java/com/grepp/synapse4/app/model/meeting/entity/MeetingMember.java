package com.grepp.synapse4.app.model.meeting.entity;

import com.grepp.synapse4.app.model.meeting.code.State;
import com.grepp.synapse4.app.model.user.entity.User;
import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
public class MeetingMember extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}