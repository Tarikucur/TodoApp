package com.todotasks.springtodoappdemo.controller;

import com.todotasks.springtodoappdemo.model.UserEntity;
import com.todotasks.springtodoappdemo.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public Iterable<UserEntity> listUsers(){
        Iterable<UserEntity> users = userRepository.findAll();
        System.out.println(users);
        return users;
    }
}
