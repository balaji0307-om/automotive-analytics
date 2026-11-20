package com.automotive.dto;

import java.time.LocalDateTime;

/**
 * DTO for Report entity.
 * Transfers report data between layers without exposing the entity.
 */
public class ReportDto {

    private Long reportId;

    // FUEL | ENGINE | ENVIRONMENTAL
    private String reportType;

    private String summary;
    private String content;
    private LocalDateTime generatedAt;

    // Vehicle details embedded for display
    private Long vehicleId;
    private String vehicleNumber;
    private String manufacturer;
    private String model;

    public ReportDto() {}

    // ---- Getters ----
    public Long getReportId() { return reportId; }
    public String getReportType() { return reportType; }
    public String getSummary() { return summary; }
    public String getContent() { return content; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public Long getVehicleId() { return vehicleId; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getManufacturer() { return manufacturer; }
    public String getModel() { return model; }

    // ---- Setters ----
    public void setReportId(Long reportId) { this.reportId = reportId; }
    public void setReportType(String reportType) { this.reportType = reportType; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setContent(String content) { this.content = content; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setModel(String model) { this.model = model; }
}
