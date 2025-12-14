package com.example.OIL.domain.mission.presentation.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record MissionCompleteRequest(
        String resultText,

        MultipartFile file
) {
}
