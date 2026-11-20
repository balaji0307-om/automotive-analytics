package com.automotive.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Report entity – represents a generated analytics report.
 * Each report belongs to one Vehicle (ManyToOne).
 * Report types: FUEL, ENGINE, ENVIRONMENTAL
 */
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    // Type of report: FUEL | ENGINE | ENVIRONMENTAL
    @Column(nullable = false, length = 30)
    private String reportType;

    // Short summary text of the report
    @Column(nullable = false, length = 255)
    private String summary;

    // Detailed content / findings stored as text
    @Column(columnDefinition = "TEXT")
    private String content;

    // When this report was generated
    @Column(nullable = false)
    private LocalDateTime generatedAt;

    /**
     * ManyToOne: many reports belong to one vehicle.
     * LAZY fetch – only load vehicle data when explicitly accessed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public Report() {}

    // Set timestamp before first persist
    @PrePersist
    public void prePersist() {
        this.generatedAt = LocalDateTime.now();
    }

    // ---- Getters ----
    public Long getReportId() { return reportId; }
    public String getReportType() { return reportType; }
    public String getSummary() { return summary; }
    public String getContent() { return content; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public Vehicle getVehicle() { return vehicle; }

    // ---- Setters ----
    public void setReportId(Long reportId) { this.reportId = reportId; }
    public void setReportType(String reportType) { this.reportType = reportType; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setContent(String content) { this.content = content; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}
