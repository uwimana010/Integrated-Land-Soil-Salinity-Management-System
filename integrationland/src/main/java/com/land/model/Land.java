package com.land.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

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

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Float size;

    private String landType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "land", cascade = CascadeType.ALL)
    private List<SoilData> soilRecords;
}
