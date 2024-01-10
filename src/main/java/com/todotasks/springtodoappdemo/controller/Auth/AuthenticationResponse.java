package com.todotasks.springtodoappdemo.controller.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    @JsonProperty("token")
    public String token;
    @JsonProperty("name")
    public String name;
    @JsonProperty("email")
    public String email;
    @JsonProperty("userId")
    public String userId;

    public AuthenticationResponse(String token, String name, String email, String userId){
        this.userId = userId;
        this.token = token;
        this.name = name;
        this.email = email;
    }
}