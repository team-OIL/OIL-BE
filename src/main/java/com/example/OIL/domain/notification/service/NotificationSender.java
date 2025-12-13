package com.example.OIL.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.example.OIL.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSender {

    public void send(User user, String title, String body) {
        try {
            String token = user.getPushToken();

            if (token == null || token.isBlank()) {
                log.warn("⚠️ 유저 {} 의 FCM 토큰 없음 → 푸시 스킵", user.getId());
                return;
            }

            Message message = Message.builder()
                    .setToken(token)
                    // FCM에서 notification 영역을 쓰고 싶으면 setNotification 사용 가능
                    .putData("title", title)
                    .putData("body", body)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);

            log.info("✅ FCM 메시지 전송 성공: {}", response);

        } catch (Exception e) {
            log.error("❌ FCM 메시지 전송 실패 (userId={}): {}", user.getId(), e.getMessage(), e);
        }
    }
}
