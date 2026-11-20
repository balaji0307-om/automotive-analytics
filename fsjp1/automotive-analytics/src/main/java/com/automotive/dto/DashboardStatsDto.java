package com.automotive.dto;

/**
 * DTO for the main dashboard summary cards.
 * Aggregated statistics shown on the dashboard home page.
 */
public class DashboardStatsDto {

    // Total number of vehicles in the system
    private long totalVehicles;

    // Average fuel consumption across all vehicles (L/100km)
    private double avgFuelConsumption;

    // Average engine temperature across all vehicles (°C)
    private double avgEngineTemperature;

    // Total number of reports generated
    private long totalReports;

    // Total CO₂ emissions (sum across all vehicles) in g/km
    private double totalCo2Emissions;

    public DashboardStatsDto() {}

    public DashboardStatsDto(long totalVehicles, double avgFuelConsumption,
                              double avgEngineTemperature, long totalReports,
                              double totalCo2Emissions) {
        this.totalVehicles = totalVehicles;
        this.avgFuelConsumption = avgFuelConsumption;
        this.avgEngineTemperature = avgEngineTemperature;
        this.totalReports = totalReports;
        this.totalCo2Emissions = totalCo2Emissions;
    }

    // ---- Getters ----
    public long getTotalVehicles() { return totalVehicles; }
    public double getAvgFuelConsumption() { return avgFuelConsumption; }
    public double getAvgEngineTemperature() { return avgEngineTemperature; }
    public long getTotalReports() { return totalReports; }
    public double getTotalCo2Emissions() { return totalCo2Emissions; }

    // ---- Setters ----
    public void setTotalVehicles(long totalVehicles) { this.totalVehicles = totalVehicles; }
    public void setAvgFuelConsumption(double avgFuelConsumption) { this.avgFuelConsumption = avgFuelConsumption; }
    public void setAvgEngineTemperature(double avgEngineTemperature) { this.avgEngineTemperature = avgEngineTemperature; }
    public void setTotalReports(long totalReports) { this.totalReports = totalReports; }
    public void setTotalCo2Emissions(double totalCo2Emissions) { this.totalCo2Emissions = totalCo2Emissions; }
}
