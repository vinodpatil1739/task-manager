package com.example.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken!");
        }

        if (!isPasswordStrong(user.getPassword())) {
            throw new RuntimeException("Password is not strong enough.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);
    }
    
    public String loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtUtil.generateToken(loginRequest.getUsername());
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    private boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        return password.matches(passwordPattern);
    }
}