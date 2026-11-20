-- ============================================================
-- Automotive Analytics Platform - Sample SQL Data
-- Run this ONLY if DataInitializer does NOT seed data,
-- or to add extra records manually.
--
-- NOTE: The application auto-seeds data via DataInitializer.java
-- on first startup. This file is for manual reference/testing.
-- ============================================================

-- Create database (run outside of the app):
-- CREATE DATABASE automotive_db;

-- ============================================================
-- Roles (auto-created by DataInitializer)
-- ============================================================
INSERT INTO roles (name) VALUES ('ROLE_ADMIN') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (name) VALUES ('ROLE_USER')  ON CONFLICT (name) DO NOTHING;

-- ============================================================
-- Users (passwords are BCrypt-encoded)
-- admin123 -> $2a$12$... (BCrypt hash)
-- user123  -> $2a$12$... (BCrypt hash)
-- Use DataInitializer for correct hashing, or hash via:
-- https://bcrypt-generator.com (cost factor 12)
-- ============================================================

-- ============================================================
-- Sample Vehicles (auto-created by DataInitializer)
-- ============================================================
INSERT INTO vehicles
    (vehicle_number, manufacturer, model, year, fuel_type, engine_type,
     mileage, fuel_consumption, engine_temperature, co2_emission, owner_name, created_date)
VALUES
    ('MH12AB1234', 'Toyota',  'Camry',    2020, 'Petrol',   'Inline-4',      45000, 7.2,  92.0,  120.0, 'Rajesh Kumar',  CURRENT_DATE),
    ('DL01CD5678', 'Honda',   'City',     2019, 'Petrol',   'Inline-4',      62000, 8.1,  89.0,  135.0, 'Priya Sharma',  CURRENT_DATE),
    ('KA03EF9012', 'Hyundai', 'Creta',    2021, 'Diesel',   'Inline-4',      38000, 5.8,  95.0,   95.0, 'Amit Patel',    CURRENT_DATE),
    ('TN07GH3456', 'Maruti',  'Swift',    2018, 'Petrol',   'Inline-3',      78000, 9.5,  91.0,  148.0, 'Divya Nair',    CURRENT_DATE),
    ('GJ05IJ7890', 'Tesla',   'Model 3',  2022, 'Electric', 'Electric Motor',25000, 15.5, 40.0,    0.0, 'Vikram Singh',  CURRENT_DATE),
    ('RJ09KL2345', 'Ford',    'EcoSport', 2017, 'Diesel',   'Inline-4',      95000, 6.2,  110.0, 102.0, 'Sunita Verma',  CURRENT_DATE),
    ('UP16MN6789', 'BMW',     '3 Series', 2023, 'Petrol',   'Inline-6',      12000, 10.8, 88.0,  172.0, 'Anand Mishra',  CURRENT_DATE),
    ('MP09OP0123', 'Tata',    'Nexon',    2021, 'Petrol',   'Inline-4',      33000, 7.8,  93.0,  125.0, 'Kavita Joshi',  CURRENT_DATE),
    ('WB20QR4567', 'Kia',     'Seltos',   2020, 'Diesel',   'Inline-4',      48000, 6.0,  115.0,  98.0, 'Suresh Babu',   CURRENT_DATE),
    ('HR26ST8901', 'MG',      'Hector',   2022, 'Petrol',   'Inline-4',      21000, 12.3, 130.0, 195.0, 'Meera Reddy',   CURRENT_DATE)
ON CONFLICT (vehicle_number) DO NOTHING;
