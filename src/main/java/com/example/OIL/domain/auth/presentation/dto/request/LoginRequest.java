package com.example.OIL.domain.auth.presentation.dto.request;

import com.example.OIL.global.util.MessageProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = MessageProperty.LOGIN_ID_NOT_BLANK)
        String email,

        @NotBlank(message = MessageProperty.PASSWORD_NOT_BLANK)
        String password,

        String pushToken
) {
}
