package com.todotasks.springtodoappdemo.controller;


import com.todotasks.springtodoappdemo.controller.Auth.AuthenticationRequestBody;
import com.todotasks.springtodoappdemo.controller.Auth.AuthenticationResponse;
import com.todotasks.springtodoappdemo.model.UserEntity;
import com.todotasks.springtodoappdemo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login",produces = "application/json")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequestBody body) {
        AuthenticationResponse response = authService.authenticate(body);
        if (response == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerUser")
    public ResponseEntity register(@RequestBody UserEntity userEntity) {
        if(authService.register(userEntity)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}

