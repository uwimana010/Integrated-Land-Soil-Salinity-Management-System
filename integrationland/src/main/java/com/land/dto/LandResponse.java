package com.land.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LandResponse {
    private Long landId;
    private String location;
    private Float size;
    private String landType;
    private Long userId;
    private String ownerName;
}
