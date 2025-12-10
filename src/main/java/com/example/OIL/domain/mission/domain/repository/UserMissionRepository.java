package com.example.OIL.domain.mission.domain.repository;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
    // 해당 유저가 오늘 이미 미션을 받았는지 확인용
    boolean existsByUserIdAndAssignedDate(Long userId, java.time.LocalDate date);
}