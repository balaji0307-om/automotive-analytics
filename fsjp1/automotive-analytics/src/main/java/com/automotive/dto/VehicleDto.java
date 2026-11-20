package com.automotive.dto;

import jakarta.validation.constraints.*;

/**
 * Data Transfer Object for Vehicle.
 * Used to transfer vehicle data between controller and service layers.
 * Keeps validation annotations separate from the entity.
 */
public class VehicleDto {

    private Long vehicleId;

    @NotBlank(message = "Vehicle number is required")
    @Size(min = 2, max = 20, message = "Vehicle number must be 2-20 characters")
    private String vehicleNumber;

    @NotBlank(message = "Manufacturer is required")
    @Size(min = 2, max = 50, message = "Manufacturer must be 2-50 characters")
    private String manufacturer;

    @NotBlank(message = "Model is required")
    @Size(min = 1, max = 50, message = "Model must be 1-50 characters")
    private String model;

    @NotNull(message = "Year is required")
    @Min(value = 1990, message = "Year must be 1990 or later")
    @Max(value = 2026, message = "Year cannot exceed 2026")
    private Integer year;

    @NotBlank(message = "Fuel type is required")
    private String fuelType;

    @NotBlank(message = "Engine type is required")
    @Size(min = 2, max = 50, message = "Engine type must be 2-50 characters")
    private String engineType;

    @NotNull(message = "Mileage is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Mileage must be zero or positive")
    private Double mileage;

    @NotNull(message = "Fuel consumption is required")
    @DecimalMin(value = "0.1", message = "Fuel consumption must be greater than 0")
    private Double fuelConsumption;

    @NotNull(message = "Engine temperature is required")
    @DecimalMin(value = "0.0", message = "Engine temperature must be positive")
    private Double engineTemperature;

    @NotNull(message = "CO2 emission is required")
    @DecimalMin(value = "0.0", message = "CO2 emission must be positive")
    private Double co2Emission;

    @NotBlank(message = "Owner name is required")
    @Size(min = 2, max = 100, message = "Owner name must be 2-100 characters")
    private String ownerName;

    private String createdDate;  // Formatted date string for display

    public VehicleDto() {}

    // ---- Getters ----
    public Long getVehicleId() { return vehicleId; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getManufacturer() { return manufacturer; }
    public String getModel() { return model; }
    public Integer getYear() { return year; }
    public String getFuelType() { return fuelType; }
    public String getEngineType() { return engineType; }
    public Double getMileage() { return mileage; }
    public Double getFuelConsumption() { return fuelConsumption; }
    public Double getEngineTemperature() { return engineTemperature; }
    public Double getCo2Emission() { return co2Emission; }
    public String getOwnerName() { return ownerName; }
    public String getCreatedDate() { return createdDate; }

    // ---- Setters ----
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setModel(String model) { this.model = model; }
    public void setYear(Integer year) { this.year = year; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public void setEngineType(String engineType) { this.engineType = engineType; }
    public void setMileage(Double mileage) { this.mileage = mileage; }
    public void setFuelConsumption(Double fuelConsumption) { this.fuelConsumption = fuelConsumption; }
    public void setEngineTemperature(Double engineTemperature) { this.engineTemperature = engineTemperature; }
    public void setCo2Emission(Double co2Emission) { this.co2Emission = co2Emission; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
}
