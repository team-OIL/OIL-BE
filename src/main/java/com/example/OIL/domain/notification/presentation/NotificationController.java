package com.example.OIL.domain.notification.presentation;

import com.example.OIL.domain.notification.presentation.dto.response.NotificationResponse;
import com.example.OIL.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getNotifications(@RequestParam Long userId) {
        return notificationService.getNotifications(userId);
    }
}
