package com.automotive.repository;

import com.automotive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 * Provides methods needed for Spring Security authentication.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used by Spring Security to load user during login
    Optional<User> findByUsername(String username);

    // Check if username is already taken (registration validation)
    boolean existsByUsername(String username);

    // Check if email is already registered
    boolean existsByEmail(String email);
}
