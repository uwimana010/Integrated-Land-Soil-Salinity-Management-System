-- Integrationland PostgreSQL Schema

CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    approved BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS land (
    land_id BIGSERIAL PRIMARY KEY,
    location VARCHAR(255) NOT NULL,
    size FLOAT NOT NULL,
    land_type VARCHAR(100),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS soil_data (
    soil_id BIGSERIAL PRIMARY KEY,
    soil_type VARCHAR(100),
    moisture_level FLOAT,
    nutrient_level FLOAT,
    salinity_level FLOAT,
    record_date DATE,
    land_id BIGINT,
    FOREIGN KEY (land_id) REFERENCES land(land_id)
);

CREATE TABLE IF NOT EXISTS crop (
    crop_id BIGSERIAL PRIMARY KEY,
    crop_name VARCHAR(100) NOT NULL,
    salinity_tolerance VARCHAR(50),
    soil_requirement VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS recommendation (
    recommendation_id BIGSERIAL PRIMARY KEY,
    recommendation_details TEXT,
    recommendation_date DATE,
    soil_id BIGINT,
    crop_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (soil_id) REFERENCES soil_data(soil_id),
    FOREIGN KEY (crop_id) REFERENCES crop(crop_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
