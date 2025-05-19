package com.grepp.synapse4.app.model.meeting.repository;

import com.grepp.synapse4.app.model.meeting.dto.AdminMeetingDto;
import com.grepp.synapse4.app.model.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("""
      select new com.grepp.synapse4.app.model.meeting.dto.AdminMeetingDto(
        m.id,
        m.title,
        m.user,
        m.description
      )
      from Meeting m
      order by m.id
    """)
    List<AdminMeetingDto> findAllWithCreatorAccount();

}
