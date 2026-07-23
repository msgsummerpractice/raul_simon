package com.example.annotationExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Biker {
    private Vehicle vehicle;

    @Autowired
    public Biker(@Qualifier("bike") Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void ride() {
        vehicle.drive();
    }
}
