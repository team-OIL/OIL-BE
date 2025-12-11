package com.example.OIL.domain.mission.domain.entity;

import com.example.OIL.domain.mission.presentation.dto.request.MissionCompleteRequest;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    private LocalDate assignedDate;  // 미션이 배정된 날짜

    @Column(columnDefinition = "TEXT")
    private String resultText;       // 수행 결과 메시지 (선택)

    private String resultImageUrl;   // 수행 결과 URL (선택)

    private boolean isCompleted;     // 수행 여부

    private LocalDateTime completedAt; // 수행 완료 시간

    @Builder
    public UserMission(User user, Mission mission, LocalDate assignedDate) {
        this.user = user;
        this.mission = mission;
        this.assignedDate = assignedDate;
        this.isCompleted = false;
    }

    // 미션 완료 처리
    public void completeMission(String text, String imgUrl) {
        this.resultText = text;
        this.resultImageUrl = imgUrl;
        this.isCompleted = true;
        this.completedAt = LocalDateTime.now();
    }
}
