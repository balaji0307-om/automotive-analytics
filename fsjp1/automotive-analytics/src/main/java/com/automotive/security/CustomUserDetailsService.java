package com.automotive.security;

import com.automotive.entity.User;
import com.automotive.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * Spring Security calls loadUserByUsername() during login to fetch
 * the user and their granted authorities (roles).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Constructor injection (preferred over @Autowired field injection)
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Load user by username for authentication.
     * @Transactional ensures the roles collection (EAGER) is loaded within session.
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user or throw exception (Spring Security handles this)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username: " + username));

        // Convert Role entities to Spring Security GrantedAuthority objects
        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // Return Spring Security's built-in User object
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled())
                .build();
    }
}
