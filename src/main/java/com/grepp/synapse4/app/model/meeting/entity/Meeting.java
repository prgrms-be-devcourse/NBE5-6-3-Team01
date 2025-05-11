package com.grepp.synapse4.app.model.meeting.entity;

import com.grepp.synapse4.app.model.meeting.entity.vote.Vote;
import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Meeting extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "meeting_id")
    private Long id;
    private String title;
    private String creatorId;
    private String description;

    @OneToMany(mappedBy = "meeting")
    private List<MeetingMember> meetingMembers = new ArrayList<>();

    @OneToMany(mappedBy = "meeting")
    private List<Vote> votes = new ArrayList<>();
}