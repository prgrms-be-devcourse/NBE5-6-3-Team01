package com.grepp.synapse4.app.model.meeting.repository;

import com.grepp.synapse4.app.model.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
