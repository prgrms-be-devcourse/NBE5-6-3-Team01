package com.grepp.synapse4.app.model.notification.repository;

import com.grepp.synapse4.app.model.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
