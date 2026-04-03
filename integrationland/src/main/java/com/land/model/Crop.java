package com.land.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "crop")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cropId;

    @NotBlank(message = "Crop name is required")
    @Column(nullable = false)
    private String cropName;

    @NotBlank(message = "Salinity tolerance is required")
    private String salinityTolerance;

    @NotBlank(message = "Soil requirement is required")
    private String soilRequirement;

    @Min(value = 0, message = "Min pH cannot be less than 0")
    @Max(value = 14, message = "Min pH cannot be more than 14")
    private Float minPh;

    @Min(value = 0, message = "Max pH cannot be less than 0")
    @Max(value = 14, message = "Max pH cannot be more than 14")
    private Float maxPh;

    @PositiveOrZero(message = "Nitrogen requirement must be positive")
    private Float nitrogenRequirement;

    @PositiveOrZero(message = "Phosphorus requirement must be positive")
    private Float phosphorusRequirement;

    @PositiveOrZero(message = "Potassium requirement must be positive")
    private Float potassiumRequirement;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "crop", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;
}
