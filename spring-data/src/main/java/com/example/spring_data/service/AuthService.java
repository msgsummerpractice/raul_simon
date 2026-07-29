package com.example.spring_data.service;

import com.example.spring_data.dto.request.SignInRequest;
import com.example.spring_data.dto.request.MfaAuthenticationRequest;
import com.example.spring_data.dto.response.LogInResponse;
import com.example.spring_data.dto.response.SignInResponse;

public interface AuthService {

    LogInResponse login (SignInRequest signInRequest);

    SignInResponse authenticateMfa(MfaAuthenticationRequest mfaAuthenticationRequest);
}
