package com.automotive.service;

import com.automotive.dto.VehicleDto;
import com.automotive.entity.Vehicle;
import com.automotive.exception.DuplicateResourceException;
import com.automotive.exception.ResourceNotFoundException;
import com.automotive.repository.VehicleRepository;
import com.automotive.util.VehicleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of VehicleService.
 * All business logic lives here – controllers are kept thin.
 */
@Service
@Transactional  // All methods run in a transaction by default
public class VehicleServiceImpl implements VehicleService {

    // Constructor injection
    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDto> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(VehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleDto getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", id));
        return VehicleMapper.toDto(vehicle);
    }

    @Override
    public VehicleDto saveVehicle(VehicleDto vehicleDto) {
        // Check for duplicate vehicle number
        String number = vehicleDto.getVehicleNumber().trim().toUpperCase();
        if (vehicleRepository.findByVehicleNumberIgnoreCase(number).isPresent()) {
            throw new DuplicateResourceException(
                    "Vehicle with number '" + number + "' already exists");
        }
        Vehicle vehicle = VehicleMapper.toEntity(vehicleDto);
        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleMapper.toDto(saved);
    }

    @Override
    public VehicleDto updateVehicle(Long id, VehicleDto vehicleDto) {
        // Find existing vehicle or throw 404
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", id));

        // Check if new vehicle number conflicts with another vehicle
        String newNumber = vehicleDto.getVehicleNumber().trim().toUpperCase();
        vehicleRepository.findByVehicleNumberIgnoreCase(newNumber).ifPresent(v -> {
            if (!v.getVehicleId().equals(id)) {
                throw new DuplicateResourceException(
                        "Vehicle number '" + newNumber + "' is already used by another vehicle");
            }
        });

        // Update fields
        VehicleMapper.updateEntity(existing, vehicleDto);
        Vehicle updated = vehicleRepository.save(existing);
        return VehicleMapper.toDto(updated);
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle", "id", id);
        }
        vehicleRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDto> searchVehicles(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllVehicles();
        }
        return vehicleRepository.searchVehicles(keyword.trim())
                .stream()
                .map(VehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDto> getVehiclesByFuelType(String fuelType) {
        return vehicleRepository.findByFuelTypeIgnoreCase(fuelType)
                .stream()
                .map(VehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDto> getVehiclesByManufacturer(String manufacturer) {
        return vehicleRepository.findByManufacturerIgnoreCase(manufacturer)
                .stream()
                .map(VehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleDto> getVehiclesByYear(Integer year) {
        return vehicleRepository.findByYear(year)
                .stream()
                .map(VehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean vehicleNumberExists(String vehicleNumber, Long excludeId) {
        return vehicleRepository.findByVehicleNumberIgnoreCase(vehicleNumber)
                .map(v -> !v.getVehicleId().equals(excludeId))
                .orElse(false);
    }
}
