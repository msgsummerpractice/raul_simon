package com.exampleboot.demo.controller;

import com.exampleboot.demo.service.UserService;
import com.exampleboot.demo.model.User;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        logger.info("UserController initialized");
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("getAllUsers endpoint called");
        return userService.getUsers();

    }


}
