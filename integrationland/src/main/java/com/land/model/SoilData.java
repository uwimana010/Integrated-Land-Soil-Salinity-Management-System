package com.land.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

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

    private String soilType;

    private Float moistureLevel;

    private Float nutrientLevel;

    private Float salinityLevel;

    private LocalDate recordDate;

    @ManyToOne
    @JoinColumn(name = "land_id")
    private Land land;

    @OneToMany(mappedBy = "soilData", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;
}
