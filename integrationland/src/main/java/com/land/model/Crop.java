package com.land.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

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

    @Column(nullable = false)
    private String cropName;

    private String salinityTolerance;

    private String soilRequirement;

    @OneToMany(mappedBy = "crop", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;
}
