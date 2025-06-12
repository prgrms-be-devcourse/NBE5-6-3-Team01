package com.grepp.synapse4.app.model.meeting.repository.vote;

import com.grepp.synapse4.app.model.meeting.entity.vote.VoteMember;
import com.grepp.synapse4.app.model.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteMemberRepository extends JpaRepository<VoteMember, Long> {

  List<VoteMember> findAllByUserIdAndIsVoted(Long userId, boolean isVoted);

  VoteMember findByVoteIdAndUserId(Long voteId, Long userId);

  List<VoteMember> findAllByVoteIdAndIsJoined(Long voteId, boolean isJoined);

  @Query("SELECT vm.isJoined FROM VoteMember vm WHERE vm.vote.id = :voteId AND vm.user.id = :userId")
  Boolean findIsJoinedByVoteIdAndUserId(Long voteId, Long userId);

  void deleteByUser(User user);
}
