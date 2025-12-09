package com.example.OIL.domain.auth.presentation;

import com.example.OIL.domain.auth.presentation.dto.request.LoginRequest;
import com.example.OIL.domain.auth.presentation.dto.request.SignUpRequest;
import com.example.OIL.domain.auth.presentation.dto.response.TokenResponse;
import com.example.OIL.domain.auth.service.LoginService;
import com.example.OIL.domain.auth.service.LogoutService;
import com.example.OIL.domain.auth.service.ReissueService;
import com.example.OIL.domain.auth.service.SignupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final SignupService signupService;
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final ReissueService reissueService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        signupService.execute(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody @Valid LoginRequest request) {
        return loginService.execute(request);
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        logoutService.execute();
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse reissue(HttpServletRequest request) {
        return reissueService.execute(request);
    }
}
