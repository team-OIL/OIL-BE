package com.example.OIL.domain.user.presentation.dto.request;

import java.time.LocalTime;

public record UpdateMissionTimeRequest(
        LocalTime MissionTime
) {
}
