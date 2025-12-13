package com.example.OIL.domain.notification.presentation.dto.response;

import java.time.LocalDate;

public record NotificationResponse(
        Long id,
        String title,
        String message,
        LocalDate createdAt,
        boolean isRead
) {
}
