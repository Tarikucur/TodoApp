package com.todotasks.springtodoappdemo.service;

import com.todotasks.springtodoappdemo.controller.Auth.AuthenticationRequestBody;
import com.todotasks.springtodoappdemo.controller.Auth.AuthenticationResponse;
import com.todotasks.springtodoappdemo.model.UserEntity;
import com.todotasks.springtodoappdemo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(TokenService tokenService,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse authenticate(AuthenticationRequestBody request){
        UserEntity userEntity = userRepository.findByEmail(request.email);
        if(userEntity != null && passwordEncoder.matches(request.password, userEntity.getPassword())){
            return new AuthenticationResponse(tokenService.generateToken(userEntity.getEmail()),
                    userEntity.getName(), userEntity.getEmail(), userEntity.getId());
        }
        return null;
    }

    public boolean register(UserEntity userEntity) {
        try {
            String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
            userEntity.setPassword(encodedPassword);
            userRepository.save(userEntity);
            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }
}
