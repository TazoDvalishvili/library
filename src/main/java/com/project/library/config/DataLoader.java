package com.project.library.config;

import com.project.library.model.User;
import com.project.library.repository.UserRepository;
import com.project.library.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DataLoader to register admin at the start of the application.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRole(Constants.Role.ADMIN);
        userRepository.save(user);
    }
}