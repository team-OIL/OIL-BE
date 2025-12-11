package com.example.OIL.domain.notification.presentation.dto.response;

import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        boolean isRead
) {
}
