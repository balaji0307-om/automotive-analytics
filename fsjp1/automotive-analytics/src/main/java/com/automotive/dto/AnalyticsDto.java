package com.automotive.dto;

/**
 * DTO carrying aggregated analytics data for the analytics pages.
 * Used for Fuel, Engine, and Environmental analytics views.
 */
public class AnalyticsDto {

    // ---- Fuel Consumption ----
    private Double avgFuelConsumption;
    private String highestFuelVehicle;       // Vehicle number
    private Double highestFuelValue;
    private String lowestFuelVehicle;        // Vehicle number
    private Double lowestFuelValue;

    // ---- Engine Behaviour ----
    private Double avgEngineTemperature;
    private long overheatingVehicleCount;    // Vehicles above threshold
    private long normalVehicleCount;         // Vehicles below threshold

    // ---- Environmental Impact ----
    private Double avgCo2Emission;
    private String highestCo2Vehicle;
    private Double highestCo2Value;
    private String lowestCo2Vehicle;
    private Double lowestCo2Value;

    public AnalyticsDto() {}

    // ---- Getters ----
    public Double getAvgFuelConsumption() { return avgFuelConsumption; }
    public String getHighestFuelVehicle() { return highestFuelVehicle; }
    public Double getHighestFuelValue() { return highestFuelValue; }
    public String getLowestFuelVehicle() { return lowestFuelVehicle; }
    public Double getLowestFuelValue() { return lowestFuelValue; }
    public Double getAvgEngineTemperature() { return avgEngineTemperature; }
    public long getOverheatingVehicleCount() { return overheatingVehicleCount; }
    public long getNormalVehicleCount() { return normalVehicleCount; }
    public Double getAvgCo2Emission() { return avgCo2Emission; }
    public String getHighestCo2Vehicle() { return highestCo2Vehicle; }
    public Double getHighestCo2Value() { return highestCo2Value; }
    public String getLowestCo2Vehicle() { return lowestCo2Vehicle; }
    public Double getLowestCo2Value() { return lowestCo2Value; }

    // ---- Setters ----
    public void setAvgFuelConsumption(Double avgFuelConsumption) { this.avgFuelConsumption = avgFuelConsumption; }
    public void setHighestFuelVehicle(String highestFuelVehicle) { this.highestFuelVehicle = highestFuelVehicle; }
    public void setHighestFuelValue(Double highestFuelValue) { this.highestFuelValue = highestFuelValue; }
    public void setLowestFuelVehicle(String lowestFuelVehicle) { this.lowestFuelVehicle = lowestFuelVehicle; }
    public void setLowestFuelValue(Double lowestFuelValue) { this.lowestFuelValue = lowestFuelValue; }
    public void setAvgEngineTemperature(Double avgEngineTemperature) { this.avgEngineTemperature = avgEngineTemperature; }
    public void setOverheatingVehicleCount(int overheatingVehicleCount) { this.overheatingVehicleCount = overheatingVehicleCount; }
    public void setNormalVehicleCount(long normalVehicleCount) { this.normalVehicleCount = normalVehicleCount; }
    public void setAvgCo2Emission(Double avgCo2Emission) { this.avgCo2Emission = avgCo2Emission; }
    public void setHighestCo2Vehicle(String highestCo2Vehicle) { this.highestCo2Vehicle = highestCo2Vehicle; }
    public void setHighestCo2Value(Double highestCo2Value) { this.highestCo2Value = highestCo2Value; }
    public void setLowestCo2Vehicle(String lowestCo2Vehicle) { this.lowestCo2Vehicle = lowestCo2Vehicle; }
    public void setLowestCo2Value(Double lowestCo2Value) { this.lowestCo2Value = lowestCo2Value; }
}
