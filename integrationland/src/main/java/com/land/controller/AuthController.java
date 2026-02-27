package com.land.controller;

import com.land.dto.LoginRequest;
import com.land.dto.RegisterRequest;
import com.land.model.User;
import com.land.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully: " + user.getEmail());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        // Simplified login for now, returning success if user exists
        // In a real app, we would use an AuthenticationManager
        return userService.findByEmail(request.getEmail())
                .map(user -> ResponseEntity.ok("Login successful for user: " + user.getFullName()))
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }
}
