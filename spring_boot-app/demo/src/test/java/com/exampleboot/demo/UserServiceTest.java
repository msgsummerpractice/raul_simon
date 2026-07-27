package com.exampleboot.demo;

import org.mockito.InjectMocks;
import com.exampleboot.demo.repository.UserRepo;
import com.exampleboot.demo.service.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import com.exampleboot.demo.model.User;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddUser() {
        User result =  userService.addUser("David", 35, "david@example.com");
        assertEquals("David", result.getName());
        assertEquals(35, result.getAge());
        verify(userRepo).addUser(any(User.class));
    }

    @Test
    public void testGetUsers() {
        List<User> mockUsers = Arrays.asList(
            new User("Mock1", 40, "mock1@example.com"),
        new User("Mock2", 23, "mock2@example.com"));
        when(userRepo.getUsers()).thenReturn(mockUsers);
        List<User> result = userService.getUsers();

        assertEquals(2, result.size());
        assertEquals("Mock1", result.get(0).getName());
        verify(userRepo).getUsers();
    }

}
