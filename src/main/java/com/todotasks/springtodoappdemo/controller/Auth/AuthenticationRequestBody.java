package com.todotasks.springtodoappdemo.controller.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationRequestBody {
    @JsonProperty("email")
    public String email;
    @JsonProperty("password")
    public String password;
}
