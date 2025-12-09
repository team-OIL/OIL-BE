package com.example.OIL.domain.auth.presentation.dto.request;

import com.example.OIL.global.util.MessageProperty;
import jakarta.validation.constraints.NotBlank;

public record TokenRequest(
        @NotBlank(message = MessageProperty.DEVICE_TOKEN_NOT_BLANK)
        String pushToken
) {
}
