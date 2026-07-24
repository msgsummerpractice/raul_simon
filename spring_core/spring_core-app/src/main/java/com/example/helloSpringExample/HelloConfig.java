package com.example.helloSpringExample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {

    @Bean
    public HelloJava myHelloJava() {
        return new HelloJava();
    }
}
