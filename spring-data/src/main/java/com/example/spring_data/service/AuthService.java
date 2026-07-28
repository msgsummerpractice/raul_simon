package com.example.spring_data.service;

import com.example.spring_data.dto.request.SignInRequest;

public interface AuthService {
    String login (SignInRequest signInRequest);
}
