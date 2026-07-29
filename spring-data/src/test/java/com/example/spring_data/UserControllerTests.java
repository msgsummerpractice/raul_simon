package com.example.spring_data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import com.example.spring_data.controller.UserController;
import com.example.spring_data.dto.request.UpdateUserRequest;
import com.example.spring_data.dto.request.UserRequest;
import com.example.spring_data.dto.response.UserResponse;
import com.example.spring_data.exception.ResourceNotFoundException;
import com.example.spring_data.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import org.springframework.http.MediaType;



@WebMvcTest(UserController.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private UserResponse userResponse;

    @BeforeEach
    public void setUp() {
        userResponse = new UserResponse(1L, "testuser", "testuser@example.com", "Test", "User", LocalDateTime.of(2005, 3, 2, 0, 0));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(userResponse));
        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUsersNoContent() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of());
        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAddUser() throws Exception {
        String userJson = "{\"username\":\"testuser\",\"email\":\"testuser@example.com\",\"password\":\"password123\",\"firstname\":\"Test\",\"lastname\":\"User\"}";
        when(userService.createUser(any(UserRequest.class))).thenReturn(userResponse);
        mockMvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
        verify(userService, times(1)).createUser(any(UserRequest.class));
    }

    @Test
    public void testAddUserWithInvalidData() throws Exception {
        UserRequest userRequest = new UserRequest("", "invalidemail", "", "Test", "User");
        String userJson = "{\"username\":\"\",\"email\":\"invalidemail\",\"password\":\"\",\"firstname\":\"Test\",\"lastname\":\"User\"}";
        when(userService.createUser(userRequest)).thenReturn(null);
        mockMvc.perform(post("/api/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest());
        verify(userService, times(0)).createUser(userRequest);
    }

    @Test
    public void testUpdateUser() throws Exception {
        String userJson = "{\"username\":\"testuser\",\"email\":\"testuser@example.com\",\"password\":\"password123\",\"firstname\":\"Test\",\"lastname\":\"User\"}";
        when(userService.updateUser(any(Long.class), any(UserRequest.class))).thenReturn(userResponse);
        mockMvc.perform(put("/api/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());
        verify(userService, times(1)).updateUser(any(Long.class), any(UserRequest.class));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        String userJson = "{\"username\":\"testuser\",\"email\":\"testuser@example.com\",\"password\":\"password123\",\"firstname\":\"Test\",\"lastname\":\"User\"}";
        when(userService.updateUser(any(Long.class), any(UserRequest.class))).thenThrow(new ResourceNotFoundException("User with ID 5 not found."));
        mockMvc.perform(put("/api/users/update/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUserWithInvalidData() throws Exception {
        String userJson = "{\"username\":\"\",\"email\":\"invalide mail\",\"password\":\"\",\"firstname\":\"Test\",\"lastname\":\"User\"}";
        when(userService.updateUser(any(Long.class), any(UserRequest.class))).thenThrow(new InvalidParameterException("Invalid data provided."));
        mockMvc.perform(put("/api/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest());
        verify(userService, times(0)).updateUser(any(Long.class), any(UserRequest.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(userService.deleteUser(any(Long.class))).thenReturn(true);
        mockMvc.perform(delete("/api/users/delete/1"))
                .andExpect(status().isNoContent());
        verify(userService, times(1)).deleteUser(any(Long.class));
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        when(userService.deleteUser(any(Long.class))).thenThrow(new ResourceNotFoundException("User with ID 5 not found."));
        mockMvc.perform(delete("/api/users/delete/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUserEmail() throws Exception {
        String emailJson = "{\"email\":\"newemail@example.com\"}";
        when(userService.patchUserEmail(any(Long.class), any(UpdateUserRequest.class))).thenReturn(userResponse);
        mockMvc.perform(patch("/api/users/update-email/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailJson))
                .andExpect(status().isOk());
        verify(userService, times(1)).patchUserEmail(any(Long.class), any(UpdateUserRequest.class));
    }

    @Test
    public void testUpdateUserEmailNotFound() throws Exception {
        String emailJson = "{\"email\":\"newemail@example.com\"}";
        when(userService.patchUserEmail(any(Long.class), any(UpdateUserRequest.class))).thenThrow(new ResourceNotFoundException("User with ID 5 not found."));
        mockMvc.perform(patch("/api/users/update-email/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUserEmailWithInvalidData() throws Exception {
        String emailJson = "{\"email\":\"invalid email\"}";
        when(userService.patchUserEmail(any(Long.class), any(UpdateUserRequest.class))).thenThrow(new InvalidParameterException("Invalid data provided."));
        mockMvc.perform(patch("/api/users/update-email/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailJson))
                .andExpect(status().isBadRequest());
        verify(userService, times(0)).patchUserEmail(any(Long.class), any(UpdateUserRequest.class));
    }
}
