package com.example.annotationExample;

import org.springframework.stereotype.Component;

@Component
public class Motorcycle implements Vehicle {
    @Override
    public void drive() {
        System.out.println("riding the Motorcycle");
    }
}
