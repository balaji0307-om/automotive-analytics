package com.automotive.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Role entity – represents a user role (e.g. ROLE_ADMIN, ROLE_USER).
 * Linked to User via a ManyToMany relationship.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Role name stored as "ROLE_ADMIN" or "ROLE_USER"
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    // Back-reference to all users who have this role
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    // ---- Getters ----
    public Long getId() { return id; }
    public String getName() { return name; }
    public Set<User> getUsers() { return users; }

    // ---- Setters ----
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setUsers(Set<User> users) { this.users = users; }
}
