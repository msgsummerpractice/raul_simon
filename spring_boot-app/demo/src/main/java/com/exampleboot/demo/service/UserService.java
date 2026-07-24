package com.exampleboot.demo.service;

import com.exampleboot.demo.model.User;
import com.exampleboot.demo.repository.UserRepo;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepo userRepo;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
        logger.info("UserService initialized");
    }

    public void addUser(String name, int age, String email) {
        User user = new User(name, age, email);
        userRepo.addUser(user);
    }

    public List<User> getUsers() {
        List<User> users = userRepo.getUsers();
        logger.info("Retrieved users: " + users.size());
        return users;
    }
    
}
