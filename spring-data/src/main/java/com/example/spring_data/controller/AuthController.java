package com.example.spring_data.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.spring_data.dto.request.MfaAuthenticationRequest;
import com.example.spring_data.dto.request.SignInRequest;
import com.example.spring_data.dto.response.LogInResponse;
import com.example.spring_data.dto.response.SignInResponse;
import com.example.spring_data.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LogInResponse> login(@RequestBody SignInRequest signInRequest) {
        LogInResponse response = authService.login(signInRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/mfa")
    public ResponseEntity<SignInResponse> authenticateMfa(@RequestBody MfaAuthenticationRequest mfaRequest) {
        SignInResponse response = authService.authenticateMfa(mfaRequest);

        return ResponseEntity.ok(response);
    }
        
}
