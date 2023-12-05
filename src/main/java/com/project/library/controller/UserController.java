package com.project.library.controller;

import com.project.library.dto.AuthDTO;
import com.project.library.dto.LoginDTO;
import com.project.library.dto.RegisterDTO;
import com.project.library.dto.UserDTO;
import com.project.library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller: managing user login/registration.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */


@RestController
@Slf4j
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        try {
            AuthDTO response = userService.registerUser(registerDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        try {
            AuthDTO response = userService.login(loginDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
