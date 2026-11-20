package com.automotive.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity – stores application login credentials and assigned roles.
 * Password is stored as BCrypt hash – never in plain text.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique username for login
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    // BCrypt-encoded password
    @Column(nullable = false)
    private String password;

    // Display name shown in UI
    @Column(nullable = false, length = 100)
    private String fullName;

    // Email for contact/display
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // Whether the account is active
    @Column(nullable = false)
    private boolean enabled = true;

    // Timestamp when the user was created
    @Column(nullable = false)
    private LocalDateTime createdDate;

    /**
     * ManyToMany: one user can have multiple roles,
     * one role can belong to multiple users.
     * The join table "user_roles" is auto-created.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns        = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {}

    // Set createdDate before first persist
    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

    // ---- Getters ----
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public boolean isEnabled() { return enabled; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public Set<Role> getRoles() { return roles; }

    // ---- Setters ----
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
