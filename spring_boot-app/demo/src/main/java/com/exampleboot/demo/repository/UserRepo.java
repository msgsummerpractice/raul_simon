package com.exampleboot.demo.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;

import com.exampleboot.demo.model.User;

@Repository
public class UserRepo {
    private List<User> users;

    private static Logger logger = LoggerFactory.getLogger(UserRepo.class);

    public UserRepo() {
        this.users = new ArrayList<>();
        this.users.addAll(Arrays.asList(
            new User("Alice", 30, "alice@example.com"),
            new User("Bob", 25, "bob@example.com")
        ));
        logger.debug("Two users already added for testing");

    }

    public void addUser(User user) {
        users.add(user);
        logger.info("User created: " + user.getName() + ", " + user.getAge() + ", " + user.getEmail());

    }

    public List<User> getUsers() {
        return users;
    }
}
