package com.todotasks.springtodoappdemo.service;

import com.todotasks.springtodoappdemo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService() {}
}
