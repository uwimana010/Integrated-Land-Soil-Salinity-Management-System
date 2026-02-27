CREATE DATABASE IF NOT EXISTS land_soil_system;
USE land_soil_system;

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE land (
    land_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(255) NOT NULL,
    size FLOAT NOT NULL,
    land_type VARCHAR(100),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE soil_data (
    soil_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    soil_type VARCHAR(100),
    moisture_level FLOAT,
    nutrient_level FLOAT,
    salinity_level FLOAT,
    record_date DATE,
    land_id BIGINT,
    FOREIGN KEY (land_id) REFERENCES land(land_id)
);

CREATE TABLE crop (
    crop_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    crop_name VARCHAR(100) NOT NULL,
    salinity_tolerance VARCHAR(50),
    soil_requirement VARCHAR(255)
);

CREATE TABLE recommendation (
    recommendation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recommendation_details TEXT,
    recommendation_date DATE,
    soil_id BIGINT,
    crop_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (soil_id) REFERENCES soil_data(soil_id),
    FOREIGN KEY (crop_id) REFERENCES crop(crop_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
