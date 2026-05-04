package com.github.crimson95.psacms.service;

import com.github.crimson95.psacms.entity.User;
import com.github.crimson95.psacms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Used to look up the application user from the database during login.
    @Autowired
    private UserRepository userRepository;

    // Spring Security calls this method automatically during authentication.
    // It is the adapter between your users table and Spring Security's UserDetails model.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Load the matching user record from the database.
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 2. Convert our User entity into a Spring Security UserDetails object.
        // This object contains the username, hashed password, and granted authorities.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),  // The stored value is a BCrypt hash, not the original plain-text password.
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))  // Attach the user's authority for authorization checks.
        );
    }
}
