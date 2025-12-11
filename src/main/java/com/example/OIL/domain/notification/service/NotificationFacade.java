package com.example.OIL.domain.notification.service;

import com.example.OIL.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationFacade {

    private final NotificationService notificationService;
    private final NotificationSender notificationSender;

    public void notify(User user, String title, String message) {

        // 1) 알림을 DB에 저장
        notificationService.save(user, title, message);

        // 2) FCM 알림 전송
        notificationSender.send(user, title, message);
    }
}
