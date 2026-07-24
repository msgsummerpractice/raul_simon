package com.example.spring_data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    private String username;

    private String email;

    @ToString.Exclude
    private String password;

    private String firstname;

    private String lastname;

    public User(String username, String email, String password, String firstname, String lastname) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
