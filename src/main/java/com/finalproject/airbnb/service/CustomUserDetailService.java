package com.finalproject.airbnb.service;

import com.finalproject.airbnb.model.entities.UserEntity;
import com.finalproject.airbnb.model.exceptions.NotFoundException;
import com.finalproject.airbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.getByEmail(username).orElseThrow(() -> new NotFoundException("user not found"));
        if (user == null) {
            throw new NotFoundException(username);
        }
        UserDetails userDetails = User.withUsername(user.getEmail()).password(user.getPassword()).authorities("USER").build();
        return userDetails;
    }
}
