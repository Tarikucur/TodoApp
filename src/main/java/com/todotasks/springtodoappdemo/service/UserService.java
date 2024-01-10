package com.todotasks.springtodoappdemo.service;

import com.todotasks.springtodoappdemo.model.UserEntity;
import com.todotasks.springtodoappdemo.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {
        UserEntity user = repository.findByEmail(username);
        return new User(
                user.getEmail(), // username
                user.getPassword(),
                Collections.emptyList() // authorities
        );
    }

    public UserDetails loadUserById(String id) {
        UserEntity user = repository.findById(id).orElse(null);
        return new User(
                user.getEmail(), // username
                user.getPassword(),
                Collections.emptyList() // authorities
        );
    }

    public UserEntity findById(String userId)
    {
        return  repository.findById(userId).orElse(null);
    }
}


