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
public class RecommendationResponse {
    private Long recommendationId;
    private String recommendationDetails;
    private LocalDate recommendationDate;
    private Long soilId;
    private Float salinityLevel;
    private Long cropId;
    private String cropName;
    private Long userId;
    private String officerName;
}
