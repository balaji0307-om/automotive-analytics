package com.automotive.controller;

import com.automotive.service.AnalyticsService;
import com.automotive.repository.VehicleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for analytics pages.
 * All analytics pages require authentication.
 */
@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final VehicleRepository vehicleRepository;

    public AnalyticsController(AnalyticsService analyticsService,
                               VehicleRepository vehicleRepository) {
        this.analyticsService = analyticsService;
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Fuel Consumption Analysis page.
     */
    @GetMapping("/fuel")
    public String fuelAnalytics(Model model) {
        model.addAttribute("analytics", analyticsService.getAnalyticsData());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("activePage", "analytics");
        return "analytics/fuel";
    }

    /**
     * Engine Behaviour Analysis page.
     */
    @GetMapping("/engine")
    public String engineAnalytics(Model model) {
        model.addAttribute("analytics", analyticsService.getAnalyticsData());
        // Pass overheating vehicles list for the table
        model.addAttribute("overheatingVehicles",
                vehicleRepository.findOverheatingVehicles(100.0));
        model.addAttribute("activePage", "analytics");
        return "analytics/engine";
    }

    /**
     * Environmental Impact Analysis page.
     */
    @GetMapping("/environmental")
    public String environmentalAnalytics(Model model) {
        model.addAttribute("analytics", analyticsService.getAnalyticsData());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("activePage", "analytics");
        return "analytics/environmental";
    }
}
