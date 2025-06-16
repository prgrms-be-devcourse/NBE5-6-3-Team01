package com.grepp.synapse4.app.model.meeting.entity.vote;

import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.app.model.user.entity.User;
import com.grepp.synapse4.infra.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
public class VoteMember {

    @Id
    @GeneratedValue
    @Column(name = "vote_member_id")
    private Long id;
    private Boolean isVoted = false;
    private Boolean isJoined;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    private Vote vote;

    // TODO: 투표에 여러 식당 등록 시 필요
//    @ManyToOne
//    @JoinColumn(name = "restaurant_id")
//    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
