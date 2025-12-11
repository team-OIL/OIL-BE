package com.example.OIL.domain.mission.domain.repository;

import com.example.OIL.domain.mission.domain.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
    // 특정 유저의 미완료 미션 중 가장 오래된 한 개
    @Query("SELECT um FROM UserMission um " +
            "WHERE um.user.id = :userId AND um.isCompleted = false " +
            "ORDER BY um.assignedDate ASC")
    Optional<UserMission> findOldestNotCompleted(Long userId);

    // 오늘 미션이 이미 생성되었는지 확인
    boolean existsByUserIdAndAssignedDate(Long userId, LocalDate assignedDate);

    long countByUserIdAndIsCompletedFalse(Long userId);

    List<UserMission> findByUserIdAndIsCompletedOrderByCompletedAtDesc(Long userId, boolean completed);
}