package com.land.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

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

    @Column(columnDefinition = "TEXT")
    private String recommendationDetails;

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
