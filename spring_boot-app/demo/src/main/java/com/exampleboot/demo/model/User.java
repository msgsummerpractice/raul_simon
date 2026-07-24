package com.exampleboot.demo.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class User {
    // @NotBlank
    private String name;

    // @NotNull
    private int age;

    // @Email
    // @NotBlank
    private String email;

    private static Logger logger = LoggerFactory.getLogger(User.class);

    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
        logger.info("User created: " + name + ", " + age + ", " + email);
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
