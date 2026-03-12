package com.land.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LandRequest {

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Size is required")
    @Positive(message = "Size must be positive")
    private Float size;

    private String landType;

    @NotNull(message = "User ID is required")
    private Long userId;
}
