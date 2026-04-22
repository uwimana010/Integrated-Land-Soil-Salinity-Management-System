package com.land.controller;

import com.land.dto.JwtResponse;
import com.land.dto.LoginRequest;
import com.land.dto.RegisterRequest;
import com.land.model.User;
import com.land.security.JwtUtil;
import com.land.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Direct registration without OTP.
     * Account is created with PENDING status.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.registerUser(request);
            return ResponseEntity.ok("Account created successfully for: " + user.getEmail() + ". Please wait for admin approval.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Login – returns JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String jwt = jwtUtil.generateToken(userDetails);

            User user = userService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return ResponseEntity.ok(JwtResponse.builder()
                    .token(jwt)
                    .id(user.getUserId())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .type("Bearer")
                    .build());

        } catch (org.springframework.security.authentication.DisabledException e) {
            return ResponseEntity.status(403)
                    .body("Your account is pending admin approval. Please contact the administrator.");
        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body("Invalid email or password.");
        }
    }
}
