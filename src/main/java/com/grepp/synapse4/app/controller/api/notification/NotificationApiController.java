package com.grepp.synapse4.app.controller.api.notification;

import com.grepp.synapse4.app.model.notification.NotificationService;
import com.grepp.synapse4.app.model.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("api/v1/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationApiController {

    private final NotificationService notificationService;

    // 유저 alarm-invite 접속 시 userId로 SseEmitter와 연결
    @GetMapping(value = "/sse/notification", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SseEmitter> send(
        @AuthenticationPrincipal CustomUserDetails userDetails
        // @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") final String lastEventId
    ){
        Long userId = userDetails.getUser().getId();
        SseEmitter sseEmitter = notificationService.connect(userId);

        return ResponseEntity.ok(sseEmitter);
    }

    @GetMapping("/list/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> notiPopup(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        Model model
    ){

        return ResponseEntity.ok().build();
    }
}
