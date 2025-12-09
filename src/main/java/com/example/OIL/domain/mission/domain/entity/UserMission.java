package com.example.OIL.domain.mission.domain.entity;

import com.example.OIL.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_missions")
@Getter
@NoArgsConstructor
public class UserMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMissionId;

    // 연결된 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 미션 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    // 완료 여부
    @Column(nullable = false)
    private boolean isCompleted;

    // 완료 시각
    private LocalDateTime completedAt;

    @Builder
    public UserMission(User user, Mission mission) {
        this.user = user;
        this.mission = mission;
        this.isCompleted = false;
    }

    /**
     * 미션 완료 처리
     */
    public void completeMission() {
        this.isCompleted = true;
        this.completedAt = LocalDateTime.now();
    }
}
