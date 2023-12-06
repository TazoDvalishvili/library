package com.project.library.service;

import com.project.library.dto.AuthDTO;
import com.project.library.dto.LoginDTO;
import com.project.library.dto.RegisterDTO;
import com.project.library.model.User;

/**
 * General user services.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */

public interface UserService {
    AuthDTO registerUser(RegisterDTO registerDTO);
    AuthDTO login(LoginDTO userDTO);

    User getCurrentUser();
}
