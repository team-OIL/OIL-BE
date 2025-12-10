package com.example.OIL.domain.mission.domain.repository;

import com.example.OIL.domain.mission.domain.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 랜덤으로 1개의 미션을 가져오는 쿼리 (MySQL 기준)
    // 데이터가 매우 많을 경우(수십만 건) 성능 이슈가 있을 수 있으나 초기 단계엔 적합
    @Query(value = "SELECT * FROM mission ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Mission> findRandomMission();
}

