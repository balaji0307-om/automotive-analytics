package com.automotive.controller;

import com.automotive.dto.VehicleDto;
import com.automotive.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * MVC controller for Vehicle CRUD operations.
 * GET/POST methods return Thymeleaf view names.
 *
 * Role restrictions:
 *   - View (list, detail): ADMIN and USER
 *   - Create/Edit/Delete: ADMIN only
 */
@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * List all vehicles.
     */
    @GetMapping
    public String listVehicles(Model model) {
        List<VehicleDto> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("activePage", "vehicles");
        return "vehicles/list";
    }

    /**
     * Show form to add a new vehicle. ADMIN only.
     */
    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddForm(Model model) {
        model.addAttribute("vehicleDto", new VehicleDto());
        model.addAttribute("activePage", "vehicles");
        model.addAttribute("formTitle", "Add New Vehicle");
        model.addAttribute("isEdit", false);
        return "vehicles/form";
    }

    /**
     * Show form to edit an existing vehicle. ADMIN only.
     */
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model) {
        VehicleDto vehicleDto = vehicleService.getVehicleById(id);
        model.addAttribute("vehicleDto", vehicleDto);
        model.addAttribute("activePage", "vehicles");
        model.addAttribute("formTitle", "Edit Vehicle");
        model.addAttribute("isEdit", true);
        return "vehicles/form";
    }

    /**
     * View vehicle details (read-only).
     */
    @GetMapping("/view/{id}")
    public String viewVehicle(@PathVariable Long id, Model model) {
        VehicleDto vehicleDto = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicleDto);
        model.addAttribute("activePage", "vehicles");
        return "vehicles/view";
    }

    /**
     * Save (create or update) a vehicle. ADMIN only.
     * Uses @Valid to trigger Bean Validation.
     * BindingResult captures validation errors.
     */
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveVehicle(@Valid @ModelAttribute("vehicleDto") VehicleDto vehicleDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttrs) {
        // If there are validation errors, go back to form
        if (bindingResult.hasErrors()) {
            model.addAttribute("activePage", "vehicles");
            model.addAttribute("formTitle", vehicleDto.getVehicleId() == null ? "Add New Vehicle" : "Edit Vehicle");
            model.addAttribute("isEdit", vehicleDto.getVehicleId() != null);
            return "vehicles/form";
        }

        try {
            if (vehicleDto.getVehicleId() == null) {
                // Create new vehicle
                vehicleService.saveVehicle(vehicleDto);
                redirectAttrs.addFlashAttribute("successMessage", "Vehicle added successfully!");
            } else {
                // Update existing vehicle
                vehicleService.updateVehicle(vehicleDto.getVehicleId(), vehicleDto);
                redirectAttrs.addFlashAttribute("successMessage", "Vehicle updated successfully!");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("activePage", "vehicles");
            model.addAttribute("formTitle", vehicleDto.getVehicleId() == null ? "Add New Vehicle" : "Edit Vehicle");
            model.addAttribute("isEdit", vehicleDto.getVehicleId() != null);
            return "vehicles/form";
        }

        return "redirect:/vehicles";
    }

    /**
     * Delete a vehicle by ID. ADMIN only.
     */
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteVehicle(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        vehicleService.deleteVehicle(id);
        redirectAttrs.addFlashAttribute("successMessage", "Vehicle deleted successfully.");
        return "redirect:/vehicles";
    }
}
