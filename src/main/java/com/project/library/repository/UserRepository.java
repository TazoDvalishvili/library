package com.project.library.repository;

import com.project.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository responsible for managing db operations for user entity.
 *
 * @author Tazo Dvalishvili
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
}
