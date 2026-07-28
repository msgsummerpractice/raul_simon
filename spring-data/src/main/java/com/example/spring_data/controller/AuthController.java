package com.example.spring_data.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.spring_data.dto.request.SignInRequest;
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
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest signInRequest) {
        String accessToken = authService.login(signInRequest);

        SignInResponse response = new SignInResponse();
        response.setAccessToken(accessToken);

        return ResponseEntity.ok(response);
    }
        
}
