package com.example.OIL.domain.user.domain.entity;

import com.example.OIL.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    // 이메일로 로그인 → 유니크 필수
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // 암호화된 비밀번호 저장
    @Column(nullable = false, length = 255)
    private String password;

    // 닉네임 존재
    @Column(nullable = false, length = 50)
    private String userName;

    // 매일 미션을 받을 시간 (예: "08:00")
    @Column(nullable = false)
    private LocalTime missionTime;

    // 알림 설정 ON/OFF
    @Column(nullable = false)
    private boolean isAlarmEnabled;

    @Column(length = 255)
    private String pushToken;

    @Builder
    public User(String email, String password, String userName, LocalTime missionTime, boolean isAlarmEnabled, String pushToken) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.missionTime = missionTime;
        this.isAlarmEnabled = isAlarmEnabled;
        this.pushToken = pushToken;
    }

    /**
     * 알림 설정 변경
     */
    public void updateAlarm(boolean enabled) {
        this.isAlarmEnabled = enabled;
    }

    /**
     * 미션 받을 시간 변경
     */
    public void updateMissionTime(LocalTime missionTime) {
        this.missionTime = missionTime;
    }

    public void updatePushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public void deletePushToken() {
        this.pushToken = null;
    }
}
