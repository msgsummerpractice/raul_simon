package com.exampleboot.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import com.exampleboot.demo.repository.UserRepo;
import com.exampleboot.demo.model.User;

@SpringBootTest
public class UserREpositoryTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    public void testAddUser() {
        User newUser = new User("Charlie", 28, "charlie@example.com");
        userRepo.addUser(newUser);
        assertEquals(3, userRepo.getUsers().size());
        assertEquals("Charlie", userRepo.getUsers().get(2).getName());
    }

    @Test
    public void testGetUsers() {
        assertEquals(2, userRepo.getUsers().size());
        assertEquals("Alice", userRepo.getUsers().get(0).getName());
        assertEquals("Bob", userRepo.getUsers().get(1).getName());
    }


}
