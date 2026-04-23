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
        // Check if admin already exists
        if (userRepository.findByEmail("clarisseuwimana31@gmail.com").isEmpty()) {
            User admin = User.builder()
                    .fullName("System Admin")
                    .email("clarisseuwimana31@gmail.com")
                    .password(passwordEncoder.encode("Kimkenny@1"))
                    .role("ROLE_ADMIN")
                    .status(ApprovalStatus.APPROVED)
                    .build();

            userRepository.save(admin);
            System.out.println("✅ Admin account created: clarisseuwimana31@gmail.com / Kimkenny@1");
        } else {
            System.out.println("ℹ️ Admin account already exists.");
        }
    }
}
