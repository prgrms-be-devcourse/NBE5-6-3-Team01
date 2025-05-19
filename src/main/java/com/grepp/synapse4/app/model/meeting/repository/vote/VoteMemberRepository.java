package com.grepp.synapse4.app.model.meeting.repository.vote;

import com.grepp.synapse4.app.model.meeting.entity.vote.VoteMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteMemberRepository extends JpaRepository<VoteMember, Long> {

  List<VoteMember> findAllByUserIdAndIsVoted(Long userId, boolean isVoted);

  VoteMember findByVoteIdAndUserId(Long voteId, Long userId);

  List<VoteMember> findAllByVoteIdAndIsJoined(Long voteId, boolean isJoined);
}
