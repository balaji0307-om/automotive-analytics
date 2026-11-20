package com.automotive.service;

import com.automotive.dto.VehicleDto;

import java.util.List;

/**
 * Service interface for Vehicle business logic.
 * Controllers depend on this interface, not the implementation.
 */
public interface VehicleService {

    List<VehicleDto> getAllVehicles();

    VehicleDto getVehicleById(Long id);

    VehicleDto saveVehicle(VehicleDto vehicleDto);

    VehicleDto updateVehicle(Long id, VehicleDto vehicleDto);

    void deleteVehicle(Long id);

    List<VehicleDto> searchVehicles(String keyword);

    List<VehicleDto> getVehiclesByFuelType(String fuelType);

    List<VehicleDto> getVehiclesByManufacturer(String manufacturer);

    List<VehicleDto> getVehiclesByYear(Integer year);

    boolean vehicleNumberExists(String vehicleNumber, Long excludeId);
}
