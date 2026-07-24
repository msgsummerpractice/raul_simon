package com.example.spring_data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.spring_data.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
