package com.automotive.repository;

import com.automotive.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Vehicle entity.
 * Contains custom JPQL queries for search and analytics.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Find by vehicle number (exact match, case-insensitive)
    Optional<Vehicle> findByVehicleNumberIgnoreCase(String vehicleNumber);

    // Search across multiple fields using a single keyword
    @Query("SELECT v FROM Vehicle v WHERE " +
           "LOWER(v.vehicleNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(v.manufacturer) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(v.model) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(v.fuelType) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Vehicle> searchVehicles(@Param("keyword") String keyword);

    // Filter by specific fields
    List<Vehicle> findByManufacturerIgnoreCase(String manufacturer);
    List<Vehicle> findByFuelTypeIgnoreCase(String fuelType);
    List<Vehicle> findByYear(Integer year);
    List<Vehicle> findByModelIgnoreCase(String model);

    // Analytics queries – aggregate data
    @Query("SELECT AVG(v.fuelConsumption) FROM Vehicle v")
    Double findAverageFuelConsumption();

    @Query("SELECT AVG(v.engineTemperature) FROM Vehicle v")
    Double findAverageEngineTemperature();

    @Query("SELECT AVG(v.co2Emission) FROM Vehicle v")
    Double findAverageCo2Emission();

    @Query("SELECT SUM(v.co2Emission) FROM Vehicle v")
    Double findTotalCo2Emission();

    // Find vehicles with engine temperature above a threshold (overheating)
    @Query("SELECT v FROM Vehicle v WHERE v.engineTemperature > :threshold")
    List<Vehicle> findOverheatingVehicles(@Param("threshold") Double threshold);

    // Find vehicle with highest fuel consumption
    @Query("SELECT v FROM Vehicle v ORDER BY v.fuelConsumption DESC LIMIT 1")
    Optional<Vehicle> findHighestFuelConsumption();

    // Find vehicle with lowest fuel consumption
    @Query("SELECT v FROM Vehicle v ORDER BY v.fuelConsumption ASC LIMIT 1")
    Optional<Vehicle> findLowestFuelConsumption();

    // Find vehicle with highest CO₂ emission
    @Query("SELECT v FROM Vehicle v ORDER BY v.co2Emission DESC LIMIT 1")
    Optional<Vehicle> findHighestCo2Emitter();

    // Find vehicle with lowest CO₂ emission
    @Query("SELECT v FROM Vehicle v ORDER BY v.co2Emission ASC LIMIT 1")
    Optional<Vehicle> findLowestCo2Emitter();
}
