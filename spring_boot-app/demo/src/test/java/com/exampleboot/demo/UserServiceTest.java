package com.exampleboot.demo;

import org.springframework.boot.test.context.SpringBootTest;

import com.exampleboot.demo.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser() {
        userService.addUser("David", 35, "david@example.com");
        assertEquals(3, userService.getUsers().size());
        assertEquals("David", userService.getUsers().get(2).getName());
    }

    @Test
    public void testGetUsers() {
        assertEquals(2, userService.getUsers().size());
    }

}
