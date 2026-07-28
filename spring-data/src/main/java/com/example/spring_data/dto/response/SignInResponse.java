package com.example.spring_data.dto.response;

import java.util.Set;

import com.example.spring_data.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class SignInResponse {
    private String accessToken;
    private Set<Role> roles;
}
