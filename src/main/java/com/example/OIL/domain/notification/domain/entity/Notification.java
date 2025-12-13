package com.example.OIL.domain.notification.domain.entity;

import com.example.OIL.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String title;
    private String message;

    private LocalDate createdAt;

    private boolean isRead;

    public Notification(User user, String title, String message) {
        this.user = user;
        this.title = title;
        this.message = message;
        this.createdAt = LocalDate.now();
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
