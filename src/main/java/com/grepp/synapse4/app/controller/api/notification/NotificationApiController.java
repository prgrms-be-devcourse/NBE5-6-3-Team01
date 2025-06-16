package com.grepp.synapse4.app.controller.api.notification;

import com.grepp.synapse4.app.model.notification.NotificationService;
import com.grepp.synapse4.app.model.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationApiController {

    private final NotificationService notificationService;

    @PreAuthorize("isAuthenticated()")
    ){
        Long userId = userDetails.getUser().getId();


        return ResponseEntity.ok().build();
    }
}
