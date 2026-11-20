package com.automotive.repository;

import com.automotive.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Report entity.
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    // Get all reports for a specific vehicle
    List<Report> findByVehicle_VehicleId(Long vehicleId);

    // Get all reports of a specific type (FUEL / ENGINE / ENVIRONMENTAL)
    List<Report> findByReportTypeIgnoreCase(String reportType);

    // Count reports by type
    long countByReportTypeIgnoreCase(String reportType);
}
