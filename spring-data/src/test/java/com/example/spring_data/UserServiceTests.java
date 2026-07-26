package com.example.spring_data;
import com.example.spring_data.repository.UserRepository;
import com.example.spring_data.service.UserService;
import com.example.spring_data.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp(){
        userRepository.findAll().clear();
        // userService.createUser(new User("testuser1", "testuser1@example.com", "password", "Test1", "User1"));
        // userService.createUser(new User("testuser2", "testuser2@example.com", "password", "Test2", "User2"));
    }

    @Test
    public void testCreateUser() {
        User newUser = new User("testuser", "testuser@example.com", "password", "Test", "User");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(newUser);
        User createdUser = userService.createUser(newUser);

        assertEquals("testuser", createdUser.getUsername());
        assertEquals("Test", createdUser.getFirstname());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testUdateUser(){
        Long userId = 1L;
        User userToUpdate = new User();
        userToUpdate.setUsername("user");
        userToUpdate.setFirstname("User");
        userToUpdate.setId(userId);
        userToUpdate.setEmail("user@example.com");

        User newUser = new User();
        newUser.setEmail("updateduser@example.com");
        newUser.setFirstname("Updated");
        newUser.setUsername("updateduser");

        Mockito.when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userToUpdate));
        Mockito.when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);
        User updatedUser = userService.updateUser(userId, newUser);


        assertEquals("updateduser", updatedUser.getUsername());
        assertEquals("Updated", updatedUser.getFirstname());
        assertFalse(updatedUser.getEmail().equals("testuser1@example.com"));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userToUpdate);
    }

    @Test
    public void testDeleteUser(){
        User userToDelete = new User("testuser1", "testuser1@example.com", "password", "Test1", "User1");
        Mockito.when(userRepository.findById(userToDelete.getId())).thenReturn(java.util.Optional.of(userToDelete));
        User deletedUser = userService.deleteUser(userToDelete.getId());

        assertEquals("testuser1", deletedUser.getUsername());
        assertEquals("Test1", deletedUser.getFirstname());
        verify(userRepository, times(1)).delete(userToDelete);
    }

    @Test
    public void testGetAllUsers(){
        List<User> users = Arrays.asList(
            new User("testuser1", "testuser1@example.com", "password", "Test1", "User1"),
            new User("testuser2", "testuser2@example.com", "password", "Test2", "User2")
        );
        Mockito.when(userRepository.findAll()).thenReturn(users);
        
        assertEquals(2, userService.getAllUsers().size());
        assertEquals("testuser1", userService.getAllUsers().get(0).getUsername());
        assertEquals("testuser2", userService.getAllUsers().get(1).getUsername());
        verify(userRepository, times(4)).findAll();
    }

    @Test
    public void testGetUserByUsername(){
        Long userId = 1L;
        User user = new User();
        user.setUsername("testuser1");
        user.setEmail("testuser1@example.com");
        user.setId(userId);
        Mockito.when(userRepository.findByUsername("testuser1")).thenReturn(user);
        User retrievedUser = userService.getUserByUsername("testuser1");

        assertEquals("testuser1", retrievedUser.getUsername());
        assertEquals("testuser1@example.com", retrievedUser.getEmail());
        assertEquals(retrievedUser.getId(), userId);
        verify(userRepository).findByUsername("testuser1");
    }

    @Test
    public void testGetUserByEmail(){
        Long userId = 1L;
        User user = new User();
        user.setUsername("testuser1");
        user.setEmail("testuser1@example.com");
        user.setId(userId);
        Mockito.when(userRepository.findByEmail("testuser1@example.com")).thenReturn(user);
        User retrievedUser = userService.getUserByEmail("testuser1@example.com");

        assertEquals("testuser1", retrievedUser.getUsername());
        assertEquals("testuser1@example.com", retrievedUser.getEmail());
        assertEquals(retrievedUser.getId(), userId);
        verify(userRepository).findByEmail("testuser1@example.com");
    }
}
