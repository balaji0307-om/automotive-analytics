package com.automotive.service;

import com.automotive.dto.ReportDto;

import java.util.List;

/**
 * Service interface for Report operations.
 */
public interface ReportService {

    // Generate a new report for a vehicle
    ReportDto generateReport(Long vehicleId, String reportType);

    // Get all reports
    List<ReportDto> getAllReports();

    // Get reports by type
    List<ReportDto> getReportsByType(String reportType);

    // Get reports for a specific vehicle
    List<ReportDto> getReportsByVehicle(Long vehicleId);

    // Get a single report
    ReportDto getReportById(Long reportId);

    // Delete a report
    void deleteReport(Long reportId);
}
