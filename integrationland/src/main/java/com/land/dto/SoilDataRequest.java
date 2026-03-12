package com.land.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SoilDataRequest {

    private String soilType;

    private Float moistureLevel;

    private Float nutrientLevel;

    @NotNull(message = "Salinity level is required")
    private Float salinityLevel;

    private LocalDate recordDate;

    @NotNull(message = "Land ID is required")
    private Long landId;
}
