package com.example.annotationExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Motorcyclist {
    @Autowired
    @Qualifier("superMotorcycle")
    private Vehicle vehicle;

    public void ride() {
        vehicle.drive();
    }
}
