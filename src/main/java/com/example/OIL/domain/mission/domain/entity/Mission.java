package com.example.OIL.domain.mission.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;

    private String content;

    private int durationTime;

    @Builder
    public Mission(String content, int durationTime) {
        this.content = content;
        this.durationTime = durationTime;
    }
}
