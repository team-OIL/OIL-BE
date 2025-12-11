package com.example.OIL.domain.notification.service;

import com.example.OIL.domain.notification.domain.entity.Notification;
import com.example.OIL.domain.notification.domain.repository.NotificationRepository;
import com.example.OIL.domain.notification.presentation.dto.response.NotificationResponse;
import com.example.OIL.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void save(User user, String title, String message) {
        Notification notification = new Notification(user, title, message);
        notificationRepository.save(notification);
    }

    public List<NotificationResponse> getNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(n -> new NotificationResponse(
                        n.getId(),
                        n.getTitle(),
                        n.getMessage(),
                        n.getCreatedAt(),
                        n.isRead()
                ))
                .toList();
    }
}

