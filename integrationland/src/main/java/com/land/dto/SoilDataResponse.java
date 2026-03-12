package com.land.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoilDataResponse {
    private Long soilId;
    private String soilType;
    private Float moistureLevel;
    private Float nutrientLevel;
    private Float salinityLevel;
    private LocalDate recordDate;
    private Long landId;
    private String landLocation;
    private String salinityCategory;  // LOW / MODERATE / HIGH / VERY_HIGH
}
