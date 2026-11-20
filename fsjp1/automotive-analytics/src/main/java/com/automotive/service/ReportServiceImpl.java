package com.automotive.service;

import com.automotive.dto.ReportDto;
import com.automotive.entity.Report;
import com.automotive.entity.Vehicle;
import com.automotive.exception.ResourceNotFoundException;
import com.automotive.repository.ReportRepository;
import com.automotive.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of ReportService.
 * Generates analytical reports based on vehicle telemetry data.
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final VehicleRepository vehicleRepository;

    public ReportServiceImpl(ReportRepository reportRepository,
                             VehicleRepository vehicleRepository) {
        this.reportRepository = reportRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public ReportDto generateReport(Long vehicleId, String reportType) {
        // Fetch the vehicle
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", vehicleId));

        Report report = new Report();
        report.setVehicle(vehicle);
        report.setReportType(reportType.toUpperCase());

        // Build summary and content based on report type
        switch (reportType.toUpperCase()) {
            case "FUEL" -> {
                report.setSummary("Fuel consumption report for " + vehicle.getVehicleNumber());
                report.setContent(buildFuelContent(vehicle));
            }
            case "ENGINE" -> {
                report.setSummary("Engine behaviour report for " + vehicle.getVehicleNumber());
                report.setContent(buildEngineContent(vehicle));
            }
            case "ENVIRONMENTAL" -> {
                report.setSummary("Environmental impact report for " + vehicle.getVehicleNumber());
                report.setContent(buildEnvironmentalContent(vehicle));
            }
            default -> throw new IllegalArgumentException("Unknown report type: " + reportType);
        }

        Report saved = reportRepository.save(report);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getAllReports() {
        return reportRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getReportsByType(String reportType) {
        return reportRepository.findByReportTypeIgnoreCase(reportType).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getReportsByVehicle(Long vehicleId) {
        return reportRepository.findByVehicle_VehicleId(vehicleId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReportDto getReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report", "id", reportId));
        return toDto(report);
    }

    @Override
    public void deleteReport(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new ResourceNotFoundException("Report", "id", reportId);
        }
        reportRepository.deleteById(reportId);
    }

    // ---- Content Builders ----

    private String buildFuelContent(Vehicle v) {
        return String.format(
            "Vehicle: %s %s %s (%s)\n" +
            "Owner: %s\n" +
            "Fuel Type: %s\n" +
            "Fuel Consumption: %.2f L/100km\n" +
            "Total Mileage: %.0f km\n" +
            "Estimated total fuel used: %.1f litres\n" +
            "Analysis: %s",
            v.getYear(), v.getManufacturer(), v.getModel(), v.getVehicleNumber(),
            v.getOwnerName(),
            v.getFuelType(),
            v.getFuelConsumption(),
            v.getMileage(),
            (v.getMileage() * v.getFuelConsumption() / 100),
            getFuelRating(v.getFuelConsumption())
        );
    }

    private String buildEngineContent(Vehicle v) {
        String status = v.getEngineTemperature() > 100 ? "OVERHEATING - Immediate service required"
                                                        : "NORMAL - Within acceptable range";
        return String.format(
            "Vehicle: %s %s %s (%s)\n" +
            "Engine Type: %s\n" +
            "Engine Temperature: %.1f°C\n" +
            "Status: %s\n" +
            "Mileage: %.0f km",
            v.getYear(), v.getManufacturer(), v.getModel(), v.getVehicleNumber(),
            v.getEngineType(),
            v.getEngineTemperature(),
            status,
            v.getMileage()
        );
    }

    private String buildEnvironmentalContent(Vehicle v) {
        return String.format(
            "Vehicle: %s %s %s (%s)\n" +
            "Fuel Type: %s\n" +
            "CO2 Emission: %.2f g/km\n" +
            "Total Mileage: %.0f km\n" +
            "Estimated total CO2 emitted: %.1f kg\n" +
            "Environmental Rating: %s",
            v.getYear(), v.getManufacturer(), v.getModel(), v.getVehicleNumber(),
            v.getFuelType(),
            v.getCo2Emission(),
            v.getMileage(),
            (v.getMileage() * v.getCo2Emission() / 1000),
            getCo2Rating(v.getCo2Emission())
        );
    }

    private String getFuelRating(double consumption) {
        if (consumption < 6.0) return "Excellent fuel efficiency";
        if (consumption < 9.0) return "Good fuel efficiency";
        if (consumption < 12.0) return "Average fuel efficiency";
        return "Poor fuel efficiency - consider servicing";
    }

    private String getCo2Rating(double co2) {
        if (co2 == 0) return "Zero emission vehicle";
        if (co2 < 100) return "Low emission - Eco-friendly";
        if (co2 < 150) return "Moderate emission";
        if (co2 < 200) return "High emission";
        return "Very high emission - Consider switching fuel type";
    }

    // ---- Entity to DTO ----
    private ReportDto toDto(Report report) {
        ReportDto dto = new ReportDto();
        dto.setReportId(report.getReportId());
        dto.setReportType(report.getReportType());
        dto.setSummary(report.getSummary());
        dto.setContent(report.getContent());
        dto.setGeneratedAt(report.getGeneratedAt());
        if (report.getVehicle() != null) {
            dto.setVehicleId(report.getVehicle().getVehicleId());
            dto.setVehicleNumber(report.getVehicle().getVehicleNumber());
            dto.setManufacturer(report.getVehicle().getManufacturer());
            dto.setModel(report.getVehicle().getModel());
        }
        return dto;
    }
}
