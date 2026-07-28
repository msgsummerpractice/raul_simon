package com.example.spring_data;
import com.example.spring_data.repository.UserRepository;
import com.example.spring_data.service.UserService;
import com.example.spring_data.dto.request.UserRequest;
import com.example.spring_data.dto.response.UserResponse;
import com.example.spring_data.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        User newUser = new User(1L,"testuser", "testuser@example.com", "password", "Test", "User", null);
        UserRequest userRequest = modelMapper.map(newUser, UserRequest.class);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(newUser);
        UserResponse createdUser = userService.createUser(userRequest);

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
        UserRequest userRequest = modelMapper.map(newUser, UserRequest.class);


        Mockito.when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(userToUpdate));
        Mockito.when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);
        UserResponse updatedUser = userService.updateUser(userId, userRequest);


        assertEquals("updateduser", updatedUser.getUsername());
        assertEquals("Updated", updatedUser.getFirstname());
        assertFalse(updatedUser.getEmail().equals("testuser1@example.com"));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userToUpdate);
    }

    @Test
    public void testDeleteUser(){
        User userToDelete = new User(1L, "testuser1", "testuser1@example.com", "password", "Test1", "User1", null);
        Mockito.when(userRepository.findById(userToDelete.getId())).thenReturn(java.util.Optional.of(userToDelete));
        boolean deleted = userService.deleteUser(userToDelete.getId());

        assertEquals(true, deleted);
        verify(userRepository, times(1)).delete(userToDelete);
    }

    @Test
    public void testGetAllUsers(){
        List<User> users = Arrays.asList(
            new User(1L, "testuser1", "testuser1@example.com", "password", "Test1", "User1", null),
            new User(2L, "testuser2", "testuser2@example.com", "password", "Test2", "User2", null)
        );
        Mockito.when(userRepository.findAll()).thenReturn(users);
        
        assertEquals(2, userService.getAllUsers().size());
        assertEquals("testuser1", userService.getAllUsers().get(0).getUsername());
        assertEquals("testuser2", userService.getAllUsers().get(1).getUsername());
        verify(userRepository, atLeastOnce()).findAll();
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

    @Test
    public void testGetTop10UsersByUsername(){
        List<User> users = Arrays.asList(
            new User(1L, "testuser1", "testuser1@example.com", "password", "Test1", "User1", null),
            new User(2L, "testuser2", "testuser2@example.com", "password", "Test2", "User2", null),
            new User(3L, "testuser3", "testuser3@example.com", "password", "Test3", "User3", null),
            new User(4L, "testuser4", "testuser4@example.com", "password", "Test4", "User4", null),
            new User(5L, "testuser5", "testuser5@example.com", "password", "Test5", "User5", null)
        );
        Mockito.when(userRepository.findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc("test")).thenReturn(users);
        List<User> retrievedUsers = userService.getUsersByUsername("test");

        assertEquals(5, retrievedUsers.size());
        assertEquals("testuser1", retrievedUsers.get(0).getUsername());
        assertEquals("testuser5", retrievedUsers.get(4).getUsername());
        verify(userRepository).findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc("test");
    }

    @Test
    public void testCountUsers(){
        Long count = 5L;
        Mockito.when(userRepository.countUsers()).thenReturn(count);
        Long userCount = userService.countUsers();

        assertEquals(count, userCount);
        verify(userRepository).countUsers();
    }
}
