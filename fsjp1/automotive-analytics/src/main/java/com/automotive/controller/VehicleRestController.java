package com.automotive.controller;

import com.automotive.dto.VehicleDto;
import com.automotive.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API controller for Vehicle operations.
 * All endpoints are under /api/vehicles.
 * Consumes and produces JSON.
 *
 * GET endpoints: accessible to all authenticated users.
 * POST/PUT/DELETE: restricted to ADMIN role.
 */
@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {

    private final VehicleService vehicleService;

    public VehicleRestController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * GET /api/vehicles – list all vehicles.
     */
    @GetMapping
    public ResponseEntity<List<VehicleDto>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    /**
     * GET /api/vehicles/{id} – get vehicle by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    /**
     * GET /api/vehicles/search?keyword=xxx – search vehicles.
     */
    @GetMapping("/search")
    public ResponseEntity<List<VehicleDto>> searchVehicles(
            @RequestParam(required = false, defaultValue = "") String keyword) {
        return ResponseEntity.ok(vehicleService.searchVehicles(keyword));
    }

    /**
     * POST /api/vehicles/add – create a new vehicle. ADMIN only.
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addVehicle(@Valid @RequestBody VehicleDto vehicleDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(collectErrors(bindingResult));
        }
        VehicleDto saved = vehicleService.saveVehicle(vehicleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * PUT /api/vehicles/update/{id} – update an existing vehicle. ADMIN only.
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id,
                                            @Valid @RequestBody VehicleDto vehicleDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(collectErrors(bindingResult));
        }
        VehicleDto updated = vehicleService.updateVehicle(id, vehicleDto);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/vehicles/delete/{id} – delete a vehicle. ADMIN only.
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Vehicle with ID " + id + " deleted successfully");
        return ResponseEntity.ok(response);
    }

    // Collect field validation errors into a map
    private Map<String, String> collectErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage()));
        return errors;
    }
}
