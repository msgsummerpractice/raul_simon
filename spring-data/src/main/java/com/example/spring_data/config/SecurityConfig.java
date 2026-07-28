package com.example.spring_data.config;

import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpMethod;
import com.example.spring_data.security.JwtAuthenticationEntryPoint;
import com.example.spring_data.security.JwtAuthenticationFilter;

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
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());

                http.exceptionHandling( exception -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint));

                http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                 return http.build();
            }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
