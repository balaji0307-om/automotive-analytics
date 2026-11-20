package com.automotive.service;

import com.automotive.dto.DashboardStatsDto;
import com.automotive.repository.ReportRepository;
import com.automotive.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of DashboardService.
 * Aggregates statistics from vehicle and report repositories.
 */
@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final VehicleRepository vehicleRepository;
    private final ReportRepository reportRepository;

    public DashboardServiceImpl(VehicleRepository vehicleRepository,
                                ReportRepository reportRepository) {
        this.vehicleRepository = vehicleRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public DashboardStatsDto getDashboardStats() {
        long totalVehicles = vehicleRepository.count();

        // Use 0.0 as fallback if no vehicles exist yet
        double avgFuel = orZero(vehicleRepository.findAverageFuelConsumption());
        double avgTemp = orZero(vehicleRepository.findAverageEngineTemperature());
        double totalCo2 = orZero(vehicleRepository.findTotalCo2Emission());
        long totalReports = reportRepository.count();

        return new DashboardStatsDto(totalVehicles, avgFuel, avgTemp, totalReports, totalCo2);
    }

    // Return 0.0 if query returns null (empty table)
    private double orZero(Double value) {
        return value != null ? value : 0.0;
    }
}
