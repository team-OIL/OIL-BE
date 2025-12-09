package com.example.OIL.domain.auth.presentation.dto.request;

import com.example.OIL.global.util.MessageProperty;
import com.example.OIL.global.util.RegexProperty;
import jakarta.validation.constraints.*;

public record SignUpRequest(
        @Email(message = MessageProperty.EMAIL_INVALID)
        @NotBlank(message = MessageProperty.EMAIL_NOT_BLANK)
        String email,

        @Pattern(
                regexp = RegexProperty.PASSWORD,
                message = MessageProperty.PASSWORD_PATTERN
        )
        @NotBlank(message = MessageProperty.PASSWORD_NOT_BLANK)
        @Size(min = 8, max = 30, message = MessageProperty.PASSWORD_SIZE)
        String password,

        @NotBlank(message = MessageProperty.MISSION_TIME_NOT_BLANK)
        String missionTime,

        @NotNull(message = MessageProperty.ALARM_NOT_NULL)
        boolean isAlarmEnabled,

        @Pattern(
                regexp = RegexProperty.USERNAME,
                message = MessageProperty.USERNAME_PATTERN
        )
        @Size(max = 20, message = MessageProperty.USERNAME_SIZE)
        String userName
) {

}
