package com.land.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "recommendation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;

    @NotBlank(message = "Recommendation details are required")
    @Column(columnDefinition = "TEXT")
    private String recommendationDetails;

    @NotNull(message = "Recommendation date is required")
    private LocalDate recommendationDate;

    @ManyToOne
    @JoinColumn(name = "soil_id")
    private SoilData soilData;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
