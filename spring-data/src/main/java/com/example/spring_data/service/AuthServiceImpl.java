package com.example.spring_data.service;

import java.security.SecureRandom;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.spring_data.dto.request.MfaAuthenticationRequest;
import com.example.spring_data.dto.request.SignInRequest;
import com.example.spring_data.dto.response.LogInResponse;
import com.example.spring_data.dto.response.SignInResponse;
import com.example.spring_data.security.JwtTokenProvider;
import com.example.spring_data.security.UserDetailsServiceImpl;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final UserDetailsServiceImpl userDetailsService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public LogInResponse login(SignInRequest signInRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    signInRequest.getUsername(),
                    signInRequest.getPassword()
            )
        );

        // SecurityContextHolder.getContext().setAuthentication(authentication);

        // String token = jwtTokenProvider.generateToken(authentication);
        // SignInResponse response = new SignInResponse();
        // response.setAccessToken(token);
        // response.setRoles(authentication.getAuthorities().stream()
        //         .map(authority -> authority.getAuthority())
        //         .collect(Collectors.toSet()));

        String otpCode = String.format("%06d", new SecureRandom().nextInt(1000000));

        userService.saveUserMfaCode(signInRequest.getUsername(), otpCode);

        System.out.println("Generated OTP Code for user " + signInRequest.getUsername() + ": " + otpCode);

        return new LogInResponse("MFA code sent to user " + signInRequest.getUsername() + ". Please check the console for the code.");
    }

    public SignInResponse authenticateMfa(MfaAuthenticationRequest mfaRequest){
        boolean isMfaValid = userService.validateAndClearMfaCode(mfaRequest.getUsername(), mfaRequest.getMfaCode());
        if (!isMfaValid) {
            throw new RuntimeException("Invalid or expired MFA code");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(mfaRequest.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        SignInResponse response = new SignInResponse();
        response.setAccessToken(token);
        response.setRoles(userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toSet()));

        return response;
    }

}
