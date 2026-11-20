package com.automotive.controller;

import com.automotive.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Dashboard controller – shows the main summary page.
 * Requires authentication (enforced by Spring Security config).
 */
@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Main dashboard page.
     * Adds stats DTO to the model for Thymeleaf to render.
     */
    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("stats", dashboardService.getDashboardStats());
        model.addAttribute("activePage", "dashboard");
        return "dashboard";  // -> templates/dashboard.html
    }
}
