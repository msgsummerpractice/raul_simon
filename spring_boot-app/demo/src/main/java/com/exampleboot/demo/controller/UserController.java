package com.exampleboot.demo.controller;

import com.exampleboot.demo.service.UserService;
import com.exampleboot.demo.config.UserControllerConfig;
import com.exampleboot.demo.model.User;
import java.util.List;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${log.text}")
    private String logSufix;

    @Autowired
    private UserControllerConfig userControllerConfig;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        logger.info("UserController initialized {}", logSufix);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User addedUser = userService.addUser(user.getName(), user.getAge(), user.getEmail());
        return ResponseEntity.ok(addedUser);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("getAllUsers endpoint called {} {}:{}", logSufix, userControllerConfig.getHostName(), userControllerConfig.getPort());
        return ResponseEntity.ok(userService.getUsers());

    }


}
