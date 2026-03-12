package com.land.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CropRequest {

    @NotBlank(message = "Crop name is required")
    private String cropName;

    private String salinityTolerance;

    private String soilRequirement;
}
