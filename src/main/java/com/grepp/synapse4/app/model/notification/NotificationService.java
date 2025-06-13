package com.grepp.synapse4.app.model.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepp.synapse4.app.model.notification.code.NotificationType;
import com.grepp.synapse4.app.model.notification.code.NotificationEventInfo;
import com.grepp.synapse4.app.model.notification.dto.NotificationDto;
import com.grepp.synapse4.app.model.notification.entity.Notification;
import com.grepp.synapse4.app.model.notification.repository.NotificationRepository;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private static final Long TIMEOUT = 60 * 60 * 1000L; // 1시간
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor(); // 쓰레드 풀 생성
    private final NotificationRepository notificationRepository;

    // SseEmiter 연결
    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        emitters.put(userId, emitter);

        // 데이터가 성공적으로 전송되면 Emitter 삭제
        emitter.onCompletion(() -> emitters.remove(userId));
        // 지정된 시간동안 어떠한 이벤트도 전송되지 않으면 Emitter 삭제
        emitter.onTimeout(() -> emitters.remove(userId));
        // 연결 오류 시 Emitter 삭제
        emitter.onError((e) -> emitters.remove(userId));

        // 최초 연결 시 더미데이터가 없으면 503 오류가 발생하기 때문에 해당 더미 데이터 생성
//        sendHandle(emitter, userId, "EventStream Created. [userId=" + userId + "]");

        return emitter;
    }

    public void sendInviteNotification(Long receiverId, NotificationDto dto, NotificationType type) {
        SseEmitter emitter = emitters.get(receiverId);
        if (emitter != null) {
            executor.execute(() -> {
                try {
                    NotificationEventInfo eventInfo = switch (type) {
                        // 초대 알림에 필요한 정보
                        case MEETING -> new NotificationEventInfo("meeting", Map.of(
                                "meetingId", dto.getMeeting().getId(),
                                "title", dto.getMeeting().getTitle()
                        ));
                        // 투표 알림에 필요한 정보
                        case VOTE -> new NotificationEventInfo("vote", Map.of(
                                "voteId", dto.getVote().getId(),
                                "meetingTitle", dto.getMeeting().getTitle(),
                                "voteTitle", dto.getVote().getTitle()
                        ));
                    };

                    emitter.send(SseEmitter.event()
                            .name(eventInfo.name()) // 결정된 이벤트 이름 사용
                            .data(new ObjectMapper().writeValueAsString(eventInfo.data())) // 결정된 데이터 사용
                    );

                } catch (IOException e) {
                    emitters.remove(receiverId);
                }
            });
        }
    }

    @Transactional
    public boolean registNotification(NotificationDto notiDto) {
        Notification notification = new Notification();
        notification.setUserId(notiDto.getUserId());
        notification.setMeeting(notiDto.getMeeting());
        notification.setType(notiDto.getType());
        notification.setRedirectURL(notiDto.getRedirectUrl());

        notificationRepository.save(notification);

        return true;
    }
}