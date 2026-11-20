package com.automotive.service;

import com.automotive.dto.AnalyticsDto;
import com.automotive.entity.Vehicle;
import com.automotive.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of AnalyticsService.
 * Overheating threshold: engine temperature > 100°C.
 */
@Service
@Transactional(readOnly = true)
public class AnalyticsServiceImpl implements AnalyticsService {

    // Engine temperature above this value is considered overheating
    private static final double OVERHEAT_THRESHOLD = 100.0;

    private final VehicleRepository vehicleRepository;

    public AnalyticsServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public AnalyticsDto getAnalyticsData() {
        AnalyticsDto dto = new AnalyticsDto();

        // ---- Fuel Consumption ----
        dto.setAvgFuelConsumption(orZero(vehicleRepository.findAverageFuelConsumption()));

        vehicleRepository.findHighestFuelConsumption().ifPresent(v -> {
            dto.setHighestFuelVehicle(v.getVehicleNumber() + " (" + v.getManufacturer() + " " + v.getModel() + ")");
            dto.setHighestFuelValue(v.getFuelConsumption());
        });

        vehicleRepository.findLowestFuelConsumption().ifPresent(v -> {
            dto.setLowestFuelVehicle(v.getVehicleNumber() + " (" + v.getManufacturer() + " " + v.getModel() + ")");
            dto.setLowestFuelValue(v.getFuelConsumption());
        });

        // ---- Engine Behaviour ----
        dto.setAvgEngineTemperature(orZero(vehicleRepository.findAverageEngineTemperature()));

        List<Vehicle> overheating = vehicleRepository.findOverheatingVehicles(OVERHEAT_THRESHOLD);
        long totalVehicles = vehicleRepository.count();
        dto.setOverheatingVehicleCount(overheating.size());
        dto.setNormalVehicleCount(totalVehicles - overheating.size());

        // ---- Environmental Impact ----
        dto.setAvgCo2Emission(orZero(vehicleRepository.findAverageCo2Emission()));

        vehicleRepository.findHighestCo2Emitter().ifPresent(v -> {
            dto.setHighestCo2Vehicle(v.getVehicleNumber() + " (" + v.getManufacturer() + " " + v.getModel() + ")");
            dto.setHighestCo2Value(v.getCo2Emission());
        });

        vehicleRepository.findLowestCo2Emitter().ifPresent(v -> {
            dto.setLowestCo2Vehicle(v.getVehicleNumber() + " (" + v.getManufacturer() + " " + v.getModel() + ")");
            dto.setLowestCo2Value(v.getCo2Emission());
        });

        return dto;
    }

    private double orZero(Double val) {
        return val != null ? val : 0.0;
    }
}
