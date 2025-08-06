package com.example.taskmanager;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}