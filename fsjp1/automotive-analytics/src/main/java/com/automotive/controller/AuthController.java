package com.automotive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handles login and access-denied pages.
 * Spring Security processes the POST /login automatically.
 */
@Controller
public class AuthController {

    /**
     * Show the login page.
     * Spring Security intercepts POST /login automatically.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // -> templates/login.html
    }

    /**
     * Show the 403 access denied page.
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/403";  // -> templates/error/403.html
    }
}
