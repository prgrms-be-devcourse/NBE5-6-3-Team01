package com.grepp.synapse4.app.model.meeting.entity.vote;

import com.grepp.synapse4.app.model.meeting.entity.Meeting;
import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "vote_id")
    private Long id;
    private String title;
    private String purpose;
    private LocalDateTime meetingDate;
    private LocalDateTime endedAt;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
