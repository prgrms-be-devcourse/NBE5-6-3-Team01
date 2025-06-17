package com.grepp.synapse4.app.model.vote.repository;

import com.grepp.synapse4.app.model.vote.entity.Vote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

  List<Vote> findAllByMeetingIdOrderByMeetingDate(Long meetingId);

}
