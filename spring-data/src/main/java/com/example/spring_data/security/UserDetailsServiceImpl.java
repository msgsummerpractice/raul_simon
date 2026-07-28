package com.example.spring_data.security;

import com.example.spring_data.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.spring_data.model.User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        return new org.springframework.security.core.userdetails.User(
                customUserDetails.getUsername(),
                customUserDetails.getPassword(),
                customUserDetails.getAuthorities()
        );
    }
}
