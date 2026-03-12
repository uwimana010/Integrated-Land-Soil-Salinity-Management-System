package com.land.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CropResponse {
    private Long cropId;
    private String cropName;
    private String salinityTolerance;
    private String soilRequirement;
}
