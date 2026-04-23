package com.land.config;

import com.land.model.ApprovalStatus;
import com.land.model.User;
import com.land.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "clarisseuwimana31@gmail.com";
        User user = userRepository.findByEmail(adminEmail).orElse(null);

        if (user == null) {
            // Create new admin if doesn't exist
            user = User.builder()
                    .fullName("System Admin")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("Kimkenny@1"))
                    .role("ROLE_ADMIN")
                    .status(ApprovalStatus.APPROVED)
                    .build();
            userRepository.save(user);
            System.out.println("✅ Admin account created: " + adminEmail);
        } else {
            // Force promote existing user to Admin and Approve them
            user.setRole("ROLE_ADMIN");
            user.setStatus(ApprovalStatus.APPROVED);
            userRepository.save(user);
            System.out.println("🚀 Existing user promoted to Admin: " + adminEmail);
        }
    }
}
