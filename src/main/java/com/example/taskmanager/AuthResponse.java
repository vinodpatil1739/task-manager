package com.example.taskmanager;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
}