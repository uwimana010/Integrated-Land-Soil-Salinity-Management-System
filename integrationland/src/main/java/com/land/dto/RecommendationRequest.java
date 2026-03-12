package com.land.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RecommendationRequest {

    private String recommendationDetails;

    private LocalDate recommendationDate;

    @NotNull(message = "Soil record ID is required")
    private Long soilId;

    private Long cropId;

    @NotNull(message = "User (officer) ID is required")
    private Long userId;
}
