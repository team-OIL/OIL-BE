package com.example.OIL.domain.mission.presentation.dto.response;

import java.time.LocalDateTime;

public record MissionDetailResponse(
        Long userMissionId,
        String missionContent,
        String resultText,
        String resultImageUrl,
        LocalDateTime completedAt
) {
}
