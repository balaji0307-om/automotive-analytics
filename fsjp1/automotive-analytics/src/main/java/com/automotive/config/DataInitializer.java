package com.automotive.config;

import com.automotive.entity.Role;
import com.automotive.entity.User;
import com.automotive.entity.Vehicle;
import com.automotive.repository.RoleRepository;
import com.automotive.repository.UserRepository;
import com.automotive.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * DataInitializer – runs once on application startup.
 * Seeds the database with:
 *  - Default roles (ROLE_ADMIN, ROLE_USER)
 *  - Default admin and user accounts
 *  - Sample vehicle data for testing
 *
 * Checks for existing data before inserting to avoid duplicates.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    // Constructor injection
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           VehicleRepository vehicleRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedRoles();
        seedUsers();
        seedVehicles();
        log.info("Data initialization completed.");
    }

    // ---- Seed Roles ----
    private void seedRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_USER"));
            log.info("Roles seeded: ROLE_ADMIN, ROLE_USER");
        }
    }

    // ---- Seed Default Users ----
    private void seedUsers() {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
            Role userRole  = roleRepository.findByName("ROLE_USER").orElseThrow();

            // Admin account: admin / admin123
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("System Administrator");
            admin.setEmail("admin@automotive.com");
            admin.setEnabled(true);
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);

            // Regular user account: user / user123
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setFullName("Regular User");
            user.setEmail("user@automotive.com");
            user.setEnabled(true);
            user.setRoles(Set.of(userRole));
            userRepository.save(user);

            log.info("Default users seeded: admin/admin123, user/user123");
        }
    }

    // ---- Seed Sample Vehicles ----
    private void seedVehicles() {
        if (vehicleRepository.count() == 0) {
            vehicleRepository.saveAll(java.util.List.of(
                buildVehicle("MH12AB1234", "Toyota", "Camry",     2020, "Petrol",   "Inline-4", 45000, 7.2,  92.0,  120.0, "Rajesh Kumar"),
                buildVehicle("DL01CD5678", "Honda",  "City",      2019, "Petrol",   "Inline-4", 62000, 8.1,  89.0,  135.0, "Priya Sharma"),
                buildVehicle("KA03EF9012", "Hyundai","Creta",     2021, "Diesel",   "Inline-4", 38000, 5.8,  95.0,   95.0, "Amit Patel"),
                buildVehicle("TN07GH3456", "Maruti", "Swift",     2018, "Petrol",   "Inline-3", 78000, 9.5,  91.0,  148.0, "Divya Nair"),
                buildVehicle("GJ05IJ7890", "Tesla",  "Model 3",   2022, "Electric", "Electric", 25000, 15.5, 40.0,    0.0, "Vikram Singh"),
                buildVehicle("RJ09KL2345", "Ford",   "EcoSport",  2017, "Diesel",   "Inline-4", 95000, 6.2,  110.0, 102.0, "Sunita Verma"),
                buildVehicle("UP16MN6789", "BMW",    "3 Series",  2023, "Petrol",   "Inline-6", 12000, 10.8, 88.0,  172.0, "Anand Mishra"),
                buildVehicle("MP09OP0123", "Tata",   "Nexon",     2021, "Petrol",   "Inline-4", 33000, 7.8,  93.0,  125.0, "Kavita Joshi"),
                buildVehicle("WB20QR4567", "Kia",    "Seltos",    2020, "Diesel",   "Inline-4", 48000, 6.0,  115.0,  98.0, "Suresh Babu"),
                buildVehicle("HR26ST8901", "MG",     "Hector",    2022, "Petrol",   "Inline-4", 21000, 12.3, 130.0, 195.0, "Meera Reddy")
            ));
            log.info("10 sample vehicles seeded.");
        }
    }

    // Helper to build a Vehicle object without boilerplate
    private Vehicle buildVehicle(String number, String make, String model,
                                  int year, String fuelType, String engineType,
                                  double mileage, double fuelConsumption,
                                  double engineTemp, double co2, String owner) {
        Vehicle v = new Vehicle();
        v.setVehicleNumber(number);
        v.setManufacturer(make);
        v.setModel(model);
        v.setYear(year);
        v.setFuelType(fuelType);
        v.setEngineType(engineType);
        v.setMileage(mileage);
        v.setFuelConsumption(fuelConsumption);
        v.setEngineTemperature(engineTemp);
        v.setCo2Emission(co2);
        v.setOwnerName(owner);
        return v;
    }
}
