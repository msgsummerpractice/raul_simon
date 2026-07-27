package com.exampleboot.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


import java.util.List;

import com.exampleboot.demo.repository.UserRepo;
import com.exampleboot.demo.model.User;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Spy
    private UserRepo userRepo;

    @Test
    public void testAddUser() {
        User newUser = new User("Charlie", 28, "charlie@example.com");
        userRepo.addUser(newUser);
        List<User> users = userRepo.getUsers();
        assertEquals(3, users.size());
        assertEquals("Charlie", userRepo.getUsers().get(2).getName());
        verify(userRepo).addUser(newUser);
    }

    @Test
    public void testGetUsers() {
        List<User> users = userRepo.getUsers();
        assertEquals(2, users.size());
        assertEquals("Alice", users.get(0).getName());
        assertEquals("Bob", users.get(1).getName());
        verify(userRepo).getUsers();
    }


}
