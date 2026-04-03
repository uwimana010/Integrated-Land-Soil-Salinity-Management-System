package com.land.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "land")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Land {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long landId;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Size is required")
    @Positive(message = "Size must be positive")
    @Column(nullable = false)
    private Float size;

    @NotBlank(message = "Land type is required")
    private String landType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "land", cascade = CascadeType.ALL)
    private List<SoilData> soilRecords;
}
