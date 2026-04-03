package com.land.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "soil_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoilData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long soilId;

    @NotBlank(message = "Soil type is required")
    private String soilType;

    @NotNull(message = "Moisture level is required")
    @PositiveOrZero(message = "Moisture level must be 0 or positive")
    private Float moistureLevel;

    @NotNull(message = "Nutrient level is required")
    @PositiveOrZero(message = "Nutrient level must be 0 or positive")
    private Float nutrientLevel;

    @NotNull(message = "Salinity level is required")
    @PositiveOrZero(message = "Salinity level must be 0 or positive")
    private Float salinityLevel;

    @NotNull(message = "pH level is required")
    @Min(value = 0, message = "pH cannot be less than 0")
    @Max(value = 14, message = "pH cannot be more than 14")
    private Float phLevel;

    @PositiveOrZero(message = "Nitrogen level must be 0 or positive")
    private Float nitrogenLevel;

    @PositiveOrZero(message = "Phosphorus level must be 0 or positive")
    private Float phosphorusLevel;

    @PositiveOrZero(message = "Potassium level must be 0 or positive")
    private Float potassiumLevel;

    private LocalDate recordDate;

    @ManyToOne
    @JoinColumn(name = "land_id")
    private Land land;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "soilData", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;
}
