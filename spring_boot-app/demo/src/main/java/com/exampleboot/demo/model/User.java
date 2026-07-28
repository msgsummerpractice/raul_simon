package com.exampleboot.demo.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class User {
    @NotBlank
    private String name;

    @Min(14)
    private int age;

    @Email
    @NotBlank
    private String email;

    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public int getAge(){
        return age;
    }

    public String getEmail(){
        return email;
    }
}
