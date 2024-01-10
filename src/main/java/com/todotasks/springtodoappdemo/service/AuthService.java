package com.todotasks.springtodoappdemo.service;

import com.todotasks.springtodoappdemo.controller.Auth.AuthenticationRequestBody;
import com.todotasks.springtodoappdemo.controller.Auth.AuthenticationResponse;
import com.todotasks.springtodoappdemo.model.UserEntity;
import com.todotasks.springtodoappdemo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthService(TokenService tokenService,
                       UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    public AuthenticationResponse authenticate(AuthenticationRequestBody request){
        UserEntity userEntity = userRepository.findByEmail(request.email);
        if(userEntity != null && userEntity.getPassword().equals(request.password)){
            return new AuthenticationResponse(tokenService.generateToken(userEntity.getEmail()),
                    userEntity.getName(), userEntity.getEmail(), userEntity.getId());
        }
        return null;
    }

    public boolean register(UserEntity userEntity) {
        try {
            userRepository.save(userEntity);
            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
