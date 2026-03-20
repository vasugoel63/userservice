package org.example.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetailsService loadUserByUsername(String username) throws UsernameNotFoundException;
}

// used to Load user data from DB during login
// Login request
// ↓
// Spring Security calls:
// loadUserByUsername(username)
// ↓
// You fetch user from DB
// ↓
// Return UserDetails (your CustomUserDetails)
// ↓
// Spring verifies password