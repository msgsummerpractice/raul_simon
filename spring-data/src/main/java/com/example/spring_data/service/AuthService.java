package com.example.spring_data.service;

import org.springframework.stereotype.Service;

import com.example.spring_data.dto.request.SignInRequest;

public interface AuthService {

    String login (SignInRequest signInRequest);
}
