package com.automotive.repository;

import com.automotive.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Role entity.
 * Spring Data JPA auto-implements CRUD methods.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Find a role by its name (e.g. "ROLE_ADMIN")
    Optional<Role> findByName(String name);
}
