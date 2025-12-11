package com.example.OIL.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.example.OIL.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSender {

    public void send(User user, String title, String messageBody) {

        // 유저의 FCM token (user 엔티티에 저장되어 있어야 함)
        String token = user.getPushToken();
        if (token == null) return;

        Message msg = Message.builder()
                .setToken(token)
                .putData("title", title)
                .putData("message", messageBody)
                .build();

        FirebaseMessaging.getInstance().sendAsync(msg);
    }
}
