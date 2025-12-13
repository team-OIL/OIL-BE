package com.example.OIL.domain.mission.presentation.dto.response;

import java.time.LocalDate;

public record MissionDetailResponse(
        Long userMissionId,
        String missionContent,
        String resultText,
        String resultImageUrl,
        LocalDate completedAt
) {
}
