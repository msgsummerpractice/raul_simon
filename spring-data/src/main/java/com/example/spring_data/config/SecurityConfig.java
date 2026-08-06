package com.example.spring_data.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;
import com.example.spring_data.security.JwtAuthenticationEntryPoint;
import com.example.spring_data.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

// @Configuration
// @EnableWebSecurity
@Component
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    // private UserDetailsService userDetailsService;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
 
    //     http
    //         .csrf(csrf -> csrf.disable())
 
    //         .authorizeHttpRequests(auth -> auth
 
    //                 .requestMatchers(
    //                         "/login",
    //                         "/css/**",
    //                         "/js/**"
    //                 )
    //                 .permitAll()
 
    //                 .requestMatchers(
    //                     "/api/users/**",
    //                     "/api/users"
    //                 )
    //                 .hasAnyRole("USER","ADMIN")
 
    //                 .anyRequest()
    //                 .authenticated()
                    
    //         )
    //         .httpBasic(Customizer.withDefaults())
 
    //         .formLogin(
    //                 form -> form
    //                         .loginPage("/login")
    //                         .usernameParameter("username")
    //                         .passwordParameter("password")
    //                         .permitAll()
    //         );
 
 
    //     return http.build();
    // }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/error")
                        .permitAll()
                        .requestMatchers(
                                org.springframework.http.HttpMethod.OPTIONS,
                                "/**")
                        .permitAll()
                        .requestMatchers("/api/users/**")
                        .hasAnyRole("USER", "ADMIN")
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
            }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
 
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of("http://localhost:4200", "https://victorious-plant-0b0b06203.7.azurestaticapps.net"));
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"));
        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type"));
        configuration.setAllowCredentials(false);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
                "/**",
                configuration);
 
        return source;
    }
}
