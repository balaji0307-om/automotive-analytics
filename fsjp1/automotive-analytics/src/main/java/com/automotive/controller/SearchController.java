package com.automotive.controller;

import com.automotive.dto.VehicleDto;
import com.automotive.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller for vehicle search functionality.
 * Supports searching by keyword, manufacturer, fuel type, and year.
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    private final VehicleService vehicleService;

    public SearchController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Search page – shows results based on query params.
     * If no params, shows the empty search form.
     */
    @GetMapping
    public String search(@RequestParam(required = false) String keyword,
                         @RequestParam(required = false) String manufacturer,
                         @RequestParam(required = false) String fuelType,
                         @RequestParam(required = false) Integer year,
                         Model model) {

        List<VehicleDto> results = null;
        String searchType = "";

        // Determine which search to perform
        if (keyword != null && !keyword.trim().isEmpty()) {
            results = vehicleService.searchVehicles(keyword.trim());
            searchType = "keyword: " + keyword;
        } else if (manufacturer != null && !manufacturer.trim().isEmpty()) {
            results = vehicleService.getVehiclesByManufacturer(manufacturer.trim());
            searchType = "manufacturer: " + manufacturer;
        } else if (fuelType != null && !fuelType.trim().isEmpty()) {
            results = vehicleService.getVehiclesByFuelType(fuelType.trim());
            searchType = "fuel type: " + fuelType;
        } else if (year != null) {
            results = vehicleService.getVehiclesByYear(year);
            searchType = "year: " + year;
        }

        model.addAttribute("results", results);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("fuelType", fuelType);
        model.addAttribute("year", year);
        model.addAttribute("activePage", "search");

        return "search";
    }
}
