package com.example.OIL.domain.mission.presentation.dto.response;

import java.time.LocalDate;

public record UserMissionResponse(
        Long userMissionId,
        String missionContent,
        int durationTime,
        LocalDate assignedDate,
        boolean completed
) {
}
