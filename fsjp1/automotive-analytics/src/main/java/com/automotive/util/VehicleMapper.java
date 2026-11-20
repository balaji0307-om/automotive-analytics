package com.automotive.util;

import com.automotive.entity.Vehicle;
import com.automotive.dto.VehicleDto;

import java.time.format.DateTimeFormatter;

/**
 * Utility class for converting between Vehicle entity and VehicleDto.
 * Keeps mapping logic in one place (Single Responsibility Principle).
 */
public class VehicleMapper {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Private constructor – utility class, not meant to be instantiated
    private VehicleMapper() {}

    /**
     * Convert Vehicle entity -> VehicleDto (for sending to view/client).
     */
    public static VehicleDto toDto(Vehicle vehicle) {
        if (vehicle == null) return null;

        VehicleDto dto = new VehicleDto();
        dto.setVehicleId(vehicle.getVehicleId());
        dto.setVehicleNumber(vehicle.getVehicleNumber());
        dto.setManufacturer(vehicle.getManufacturer());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setFuelType(vehicle.getFuelType());
        dto.setEngineType(vehicle.getEngineType());
        dto.setMileage(vehicle.getMileage());
        dto.setFuelConsumption(vehicle.getFuelConsumption());
        dto.setEngineTemperature(vehicle.getEngineTemperature());
        dto.setCo2Emission(vehicle.getCo2Emission());
        dto.setOwnerName(vehicle.getOwnerName());
        if (vehicle.getCreatedDate() != null) {
            dto.setCreatedDate(vehicle.getCreatedDate().format(DATE_FORMATTER));
        }
        return dto;
    }

    /**
     * Convert VehicleDto -> Vehicle entity (for saving to database).
     * Does NOT set vehicleId or createdDate (handled by JPA).
     */
    public static Vehicle toEntity(VehicleDto dto) {
        if (dto == null) return null;

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(dto.getVehicleNumber().trim().toUpperCase());
        vehicle.setManufacturer(dto.getManufacturer().trim());
        vehicle.setModel(dto.getModel().trim());
        vehicle.setYear(dto.getYear());
        vehicle.setFuelType(dto.getFuelType().trim());
        vehicle.setEngineType(dto.getEngineType().trim());
        vehicle.setMileage(dto.getMileage());
        vehicle.setFuelConsumption(dto.getFuelConsumption());
        vehicle.setEngineTemperature(dto.getEngineTemperature());
        vehicle.setCo2Emission(dto.getCo2Emission());
        vehicle.setOwnerName(dto.getOwnerName().trim());
        return vehicle;
    }

    /**
     * Copy fields from DTO into an existing entity (for update operations).
     * Preserves vehicleId and createdDate from the original entity.
     */
    public static void updateEntity(Vehicle vehicle, VehicleDto dto) {
        vehicle.setVehicleNumber(dto.getVehicleNumber().trim().toUpperCase());
        vehicle.setManufacturer(dto.getManufacturer().trim());
        vehicle.setModel(dto.getModel().trim());
        vehicle.setYear(dto.getYear());
        vehicle.setFuelType(dto.getFuelType().trim());
        vehicle.setEngineType(dto.getEngineType().trim());
        vehicle.setMileage(dto.getMileage());
        vehicle.setFuelConsumption(dto.getFuelConsumption());
        vehicle.setEngineTemperature(dto.getEngineTemperature());
        vehicle.setCo2Emission(dto.getCo2Emission());
        vehicle.setOwnerName(dto.getOwnerName().trim());
    }
}
