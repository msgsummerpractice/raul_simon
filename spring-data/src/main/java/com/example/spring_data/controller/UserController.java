package com.example.spring_data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import com.example.spring_data.dto.request.UpdateUserRequest;
import com.example.spring_data.dto.request.UserRequest;
import com.example.spring_data.dto.response.UserResponse;
import com.example.spring_data.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
// @Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUsers(@Min(0) @RequestParam(defaultValue = "0") int page,
                                           @Min(1) @RequestParam(defaultValue = "10") int size) {
        Page<UserResponse> usersPage = userService.getPaginatedUsers(page, size);
        if (!usersPage.isEmpty()) {
            return ResponseEntity.ok(usersPage);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> allUsers = userService.getAllUsers();
        if (!allUsers.isEmpty()){
            return ResponseEntity.ok(allUsers);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponse> getUserById(@Min(1) @PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest user) {
        UserResponse createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest, @Min(1) @PathVariable Long id) {
            UserResponse updatedUser = userService.updateUser(id, userRequest);
            return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> deleteUser(@Min(1) @PathVariable Long id) {
            userService.deleteUser(id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PatchMapping("/update-email/{id}")
    public ResponseEntity<UserResponse> updateUserEmail(@Min(1) @PathVariable Long id, @Valid @RequestBody UpdateUserRequest userRequest) {
        UserResponse updatedUser = userService.patchUserEmail(id, userRequest);

        return ResponseEntity.ok(updatedUser);
    }
}
