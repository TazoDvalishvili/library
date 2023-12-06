package com.project.library.service;

import com.project.library.config.JwtService;
import com.project.library.dto.AuthDTO;
import com.project.library.dto.LoginDTO;
import com.project.library.dto.RegisterDTO;
import com.project.library.model.User;
import com.project.library.repository.UserRepository;
import com.project.library.utils.Constants;
import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl implements UserService interface.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public AuthDTO registerUser(RegisterDTO registerDTO) throws RuntimeException{
        if(userRepository.existsByUsername(registerDTO.getUsername())) {
            log.error("Username: " + registerDTO.getUsername() + " already exists!");
            throw new InternalException("Username: " + registerDTO.getUsername() + " already exists!");
        }
        try {
            User user = new User();
            user.setUsername(registerDTO.getUsername());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setRole(Constants.Role.USER);
            userRepository.save(user);
            return login(new LoginDTO(registerDTO.getUsername(), registerDTO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof InternalException) {
                throw e;
            }
            throw new RuntimeException("Cannot register provided user");
        }
    }

    @Override
    public AuthDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return AuthDTO.builder()
                    .accessToken(jwtService.GenerateToken(loginDTO.getUsername())).build();
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Override
    public User getCurrentUser() {
        try {
            String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByUsername(currentUserName);
        } catch (Exception e) {
            throw new InternalException("Cannot get user from session");
        }
    }
}
