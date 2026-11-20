package com.automotive.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Vehicle entity – core domain object.
 * Stores all telemetry and registration data for a vehicle.
 */
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    // e.g. "MH12AB1234" – must be unique
    @Column(nullable = false, unique = true, length = 20)
    @NotBlank(message = "Vehicle number is required")
    private String vehicleNumber;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Model is required")
    private String model;

    @Column(name = "vehicle_year", nullable = false)
    @Min(value = 1990, message = "Year must be 1990 or later")
    @Max(value = 2026, message = "Year cannot be in the future")
    private Integer year;

    // Petrol, Diesel, Electric, Hybrid, CNG
    @Column(nullable = false, length = 20)
    @NotBlank(message = "Fuel type is required")
    private String fuelType;

    // Engine type: Inline-4, V6, V8, Electric Motor, etc.
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Engine type is required")
    private String engineType;

    // Total distance driven in km
    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "Mileage must be positive")
    private Double mileage;

    // Litres per 100 km (or kWh per 100 km for EVs)
    @Column(nullable = false)
    @DecimalMin(value = "0.1", message = "Fuel consumption must be positive")
    private Double fuelConsumption;

    // Engine temperature in Celsius
    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "Engine temperature must be positive")
    private Double engineTemperature;

    // CO₂ emission in g/km
    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "CO2 emission must be positive")
    private Double co2Emission;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Owner name is required")
    private String ownerName;

    // Auto-set on first save
    @Column(nullable = false)
    private LocalDate createdDate;

    /**
     * OneToMany: one vehicle can have many reports.
     * CascadeType.ALL: deleting a vehicle removes its reports.
     */
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    public Vehicle() {}

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDate.now();
    }

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
    public LocalDate getCreatedDate() { return createdDate; }
    public List<Report> getReports() { return reports; }

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
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
    public void setReports(List<Report> reports) { this.reports = reports; }
}
