package com.automotive.config;

import com.automotive.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security configuration.
 * Defines:
 *  - URL access rules (who can access what)
 *  - Custom login/logout pages
 *  - BCrypt password encoding
 *  - Method-level security (@PreAuthorize)
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // Enables @PreAuthorize on service/controller methods
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * BCrypt password encoder – hashes passwords with cost factor 12.
     * This bean is used both here and in DataInitializer.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * DaoAuthenticationProvider – connects Spring Security to our DB user store.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * AuthenticationManager – needed for programmatic authentication if required.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Main security filter chain – defines HTTP security rules.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())

            // ---- Authorization Rules ----
            .authorizeHttpRequests(auth -> auth
                // Public resources (login page, static files, H2 console)
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()

                // ADMIN-only: add, edit, delete vehicles
                .requestMatchers("/vehicles/new", "/vehicles/save",
                                  "/vehicles/edit/**", "/vehicles/delete/**").hasRole("ADMIN")

                // Admin-only API mutations
                .requestMatchers("/api/vehicles/add",
                                  "/api/vehicles/update/**",
                                  "/api/vehicles/delete/**").hasRole("ADMIN")

                // Everything else requires authentication
                .anyRequest().authenticated()
            )

            // ---- Custom Login Form ----
            .formLogin(form -> form
                .loginPage("/login")                     // Our custom login page
                .loginProcessingUrl("/login")            // POST URL Spring Security handles
                .defaultSuccessUrl("/dashboard", true)   // Redirect after successful login
                .failureUrl("/login?error=true")         // Redirect on bad credentials
                .permitAll()
            )

            // ---- Logout ----
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // ---- Access Denied Handler ----
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
            )

            // ---- H2 Console (dev only) ----
            // Allow H2 console iframe and disable CSRF for /h2-console
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            );

        return http.build();
    }
}
