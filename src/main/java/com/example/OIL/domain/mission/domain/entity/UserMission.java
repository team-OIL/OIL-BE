package com.example.OIL.domain.mission.domain.entity;

import com.example.OIL.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
public class UserMission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    private LocalDate assignedDate; // 미션 부여 날짜

    @Column(columnDefinition = "TEXT")
    private String resultText;      // 수행 결과 텍스트
    private String resultImageUrl;  // 수행 인증샷 URL (선택)

    private boolean isCompleted;    // 수행 완료 여부
    private LocalDateTime completedAt; // 수행 완료 시간

    @Builder
    public UserMission(User user, Mission mission, LocalDate assignedDate) {
        this.user = user;
        this.mission = mission;
        this.assignedDate = assignedDate;
        this.isCompleted = false;
    }

    // 수행 완료 처리 메서드 (비즈니스 로직용)
    public void completeMission(String resultText, String imageUrl) {
        this.resultText = resultText;
        this.resultImageUrl = imageUrl;
        this.isCompleted = true;
        this.completedAt = LocalDateTime.now();
    }
}
