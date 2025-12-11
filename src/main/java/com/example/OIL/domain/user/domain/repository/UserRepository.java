package com.example.OIL.domain.user.domain.repository;

import com.example.OIL.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByMissionTime(LocalTime missionTime);
}
