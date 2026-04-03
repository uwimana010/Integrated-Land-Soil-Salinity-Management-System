# Integrationland: Business Rules & Advanced Logic

## 1. Data Validation Rules
All incoming API payloads are heavily validated to ensure database integrity at the entry point:
- **User**: `fullName` must not be blank, `email` must be valid format, `password` and `role` must not be blank.
- **Land**: `size` must be a positive number. `location` and `landType` are required.
- **SoilData**: `moistureLevel`, `nutrientLevel`, and `salinityLevel` must be 0 or positive. `soilType` is required.
- **Crop**: `cropName`, `salinityTolerance`, and `soilRequirement` must be specified.
- **Recommendation**: `recommendationDetails` and `recommendationDate` are required.

## 2. Advanced Filtering Options
Clients can retrieve tailored lists of resources through optional query parameters:
- **Lands**: `GET /api/lands?landType={type}`
- **Soil Data**: optionally filter by soil type or moisture ranges:
  `GET /api/soil-data?soilType={type}`
  `GET /api/soil-data?minMoisture={min}&maxMoisture={max}`
- **Crops**: find compatible crops via salinity and soil needs:
  `GET /api/crops?salinityTolerance={tolerance}`
  `GET /api/crops?soilRequirement={req}`

## 3. Crop Recommendation Workflow
A core business rule provides dynamic logic for suggesting crops based on matching soil data parameters.
- **Trigger**: The workflow is intentionally designed as an explicit API endpoint (`POST /api/recommendations/generate/{soilId}`).
- **Engine Logic**: `RecommendationEngineService` evaluates all available `Crop` profiles against the target `SoilData`.
  - **Soil Matching**: The `Crop`'s exact `soilRequirement` must match the `SoilData`'s `soilType`.
  - **Salinity Matching**: The `Crop`'s `salinityTolerance` ("LOW", "MEDIUM", "HIGH") is mapped against the actual `salinityLevel` float value:
    - `HIGH`: salinity >= 8.0
    - `MEDIUM`: 4.0 <= salinity < 8.0
    - `LOW`: salinity < 4.0
- **Outcome**: For every crop that matches these rules, a new `Recommendation` record is created and linked to the `User`, the specific `SoilData`, and the chosen `Crop`.
