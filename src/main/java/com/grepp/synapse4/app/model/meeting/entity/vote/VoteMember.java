package com.grepp.synapse4.app.model.meeting.entity.vote;

import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.app.model.user.entity.User;
import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter @ToString
public class VoteMember extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "vote_member_id")
    private Long id;
    private Boolean isVoted;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote meetingVote;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
