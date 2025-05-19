package com.grepp.synapse4.app.model.meeting.repository;

import com.grepp.synapse4.app.model.meeting.code.State;
import com.grepp.synapse4.app.model.meeting.entity.MeetingMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingMemberRepository extends JpaRepository<MeetingMember, Long> {
  List<MeetingMember> findAllByUserIdAndStateAndDeletedAtIsNull(Long userId, State state);
  List<MeetingMember> findAllByMeetingIdAndStateAndDeletedAtIsNull(Long meeting_id, State state);

  Optional<MeetingMember> findByMeetingIdAndUserId(Long meetingId, Long userId);

  Boolean existsAllByMeetingIdAndUserId(Long meetingId, Long userId);

  Integer countByMeetingIdAndState(Long meetingId, State state);
}