package com.todotasks.springtodoappdemo.controller;

import com.todotasks.springtodoappdemo.dto.UserUpdateDTO;
import com.todotasks.springtodoappdemo.model.UserEntity;
import com.todotasks.springtodoappdemo.repository.TodoRepository;
import com.todotasks.springtodoappdemo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public UserController(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    @GetMapping("/users")
    public Iterable<UserEntity> listUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        return userOptional
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable String userId, @RequestBody UserUpdateDTO updatedUserDTO) {
        Optional<UserEntity> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            UserEntity existingUser = existingUserOptional.get();

            if (updatedUserDTO.getName() != null) {
                existingUser.setName(updatedUserDTO.getName());
            }

            if (updatedUserDTO.getPassword() != null) {
                existingUser.setPassword(updatedUserDTO.getPassword());
            }

            userRepository.save(existingUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            todoRepository.deleteByUserId(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
