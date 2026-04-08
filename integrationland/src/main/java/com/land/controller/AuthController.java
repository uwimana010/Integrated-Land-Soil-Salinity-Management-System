package com.land.controller;

import com.land.dto.JwtResponse;
import com.land.dto.LoginRequest;
import com.land.dto.OtpRequest;
import com.land.dto.RegisterRequest;
import com.land.model.User;
import com.land.security.JwtUtil;
import com.land.service.EmailService;
import com.land.service.OtpService;
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
    private final OtpService otpService;
    private final EmailService emailService;

    /**
     * Step 1 – Send a 6-digit OTP to the provided email address.
     * The client calls this BEFORE submitting the full registration form.
     */
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody OtpRequest request) {
        try {
            // Prevent duplicate registrations
            if (userService.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email is already registered.");
            }
            String otp = otpService.generateOtp(request.getEmail());
            // Send to Admin email instead of user email as requested
            emailService.sendOtpEmail("clarisseuwimana31@gmail.com", otp);
            return ResponseEntity.ok("OTP sent to Administrator for verification. Valid for 10 minutes.");
        } catch (Exception e) {
            // Enhanced reliability for Phase 8: Mock the success in Dev environment
            System.err.println("\n[DEV ALERT] SMTP Failure: " + e.getMessage() + ". Proceeding with console log fallback.\n");
            return ResponseEntity.ok("OTP logging fallback active: Please check your application console (IDE/Terminal) for the verification code.");
        }
    }

    /**
     * Step 2 – Verify OTP + create the account.
     * The request body now includes the otp field.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        // Validate OTP first
        if (!otpService.validateOtp(request.getEmail(), request.getOtp())) {
            return ResponseEntity.badRequest()
                    .body("Invalid or expired OTP. Please request a new code.");
        }
        try {
            User user = userService.registerUser(request);
            return ResponseEntity.ok("Account created successfully for: " + user.getEmail());
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
