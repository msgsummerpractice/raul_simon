package com.example.spring_data.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.spring_data.repository.UserRepository;
import com.example.spring_data.dto.request.UpdateUserRequest;
import com.example.spring_data.dto.request.UserRequest;
import com.example.spring_data.dto.response.UserResponse;
import com.example.spring_data.excetion.InvalidParameterException;
import com.example.spring_data.excetion.ResourceNotFoundException;
import com.example.spring_data.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest userRequest) {
        ModelMapper modelMapper = new ModelMapper();

        User user = modelMapper.map(userRequest, User.class);
        User savedUser = userRepository.save(user);

        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setModifiedAt(LocalDateTime.now());

        return userResponse;
    }

    public UserResponse patchUserEmail(Long id, UpdateUserRequest userRequest) {
        ModelMapper modelMapper = new ModelMapper();
        User existingUser = userRepository.findById(id).orElse(null);
        String newEmail = userRequest.getEmail();

        if (existingUser != null && newEmail != null && !newEmail.isEmpty()){
            existingUser.setEmail(newEmail);
        }
        else if (existingUser == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        else {
            throw new InvalidParameterException("Email cannot be null or empty.");
        }

        User updatedUser = userRepository.save(existingUser);
        UserResponse userResponse = modelMapper.map(updatedUser, UserResponse.class);
        userResponse.setModifiedAt(LocalDateTime.now());

        return userResponse;
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userRequest, User.class);
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());

            userRepository.save(existingUser);

            UserResponse userResponse = modelMapper.map(existingUser, UserResponse.class);
            userResponse.setModifiedAt(LocalDateTime.now());
            return userResponse;
        }
        throw new ResourceNotFoundException("User with ID " + id + " not found.");
    }

    public boolean deleteUser(Long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            userRepository.delete(existingUser);
            return true;
        }
        throw new ResourceNotFoundException("User with ID " + id + " not found.");
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }

        ModelMapper modelMapper = new ModelMapper();
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;
    }

    public Page<UserResponse> getPaginatedUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        ModelMapper modelMapper = new ModelMapper();

        return usersPage.map(user -> modelMapper.map(user, UserResponse.class));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        if (userRepository.findByUsername(username) == null) {
            throw new ResourceNotFoundException("User with username " + username + " not found.");
        }

        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        if (userRepository.findByEmail(email) == null) {
            throw new ResourceNotFoundException("User with email " + email + " not found.");
        }
        
        return userRepository.findByEmail(email);
    }

    public List<User> getUsersByUsername(String username) {
        return userRepository.findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc(username);
    }

    public Long countUsers() {
        return userRepository.countUsers();
    }
}
