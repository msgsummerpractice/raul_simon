package com.example.spring_data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 
        http
            .csrf(csrf -> csrf.disable())
 
            .authorizeHttpRequests(auth -> auth
 
                    .requestMatchers(
                            "/login",
                            "/css/**",
                            "/js/**"
                    )
                    .permitAll()
 
                    .requestMatchers(
                        "/api/users/**",
                        "/api/users"
                    )
                    .hasAnyRole("USER","ADMIN")
 
                    .anyRequest()
                    .authenticated()
                    
            )
            .httpBasic(Customizer.withDefaults())
 
            .formLogin(
                    form -> form
                            .loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .permitAll()
            );
 
 
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
