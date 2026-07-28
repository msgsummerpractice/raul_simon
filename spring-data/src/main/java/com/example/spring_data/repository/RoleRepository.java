package com.example.spring_data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_data.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {   

}
