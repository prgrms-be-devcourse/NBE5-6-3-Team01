package com.grepp.synapse4.app.model.meeting.repository;

import com.grepp.synapse4.app.model.meeting.code.State;
import com.grepp.synapse4.app.model.meeting.dto.AdminMeetingMemberDto;
import com.grepp.synapse4.app.model.meeting.entity.MeetingMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingMemberRepository extends JpaRepository<MeetingMember, Long> {
  List<MeetingMember> findAllByUserIdAndStateAndDeletedAtIsNull(Long userId, State state);
  List<MeetingMember> findAllByMeetingIdAndStateAndDeletedAtIsNull(Long meeting_id, State state);

  Optional<MeetingMember> findByMeetingIdAndUserId(Long meetingId, Long userId);

  Boolean existsAllByMeetingIdAndUserId(Long meetingId, Long userId);

  Integer countByMeetingIdAndState(Long meetingId, State state);

  List<MeetingMember> findAllByMeetingIdAndState(Long meetingId, State state);

  Integer countByMeetingId(Long meetingId);

  // 관리자페이지 모임멤버보기
  @Query("""
      select new com.grepp.synapse4.app.model.meeting.dto.AdminMeetingMemberDto(
        mm.id,
        mm.meeting.id,
        mm.user.nickname
      )
      from MeetingMember mm
      where mm.meeting.id = :meetingId
    """)
  List<AdminMeetingMemberDto> findUserNicknamesByMeetingId(@Param("meetingId") Long meetingId);

  List<MeetingMember> findAllByMeetingId(Long meetingId);
}