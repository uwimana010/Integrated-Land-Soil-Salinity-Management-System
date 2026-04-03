package com.land.controller;

import com.land.model.SoilData;
import com.land.service.SoilDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/soil-data")
@RequiredArgsConstructor
public class SoilDataController {

    private final SoilDataService soilDataService;

    @GetMapping
    public ResponseEntity<List<SoilData>> getAllSoilData(
            @RequestParam(required = false) String soilType,
            @RequestParam(required = false) Float minMoisture,
            @RequestParam(required = false) Float maxMoisture,
            @RequestParam(required = false) Long landId,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate startDate,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate endDate) {
        
        if (soilType != null && !soilType.isEmpty()) {
            return ResponseEntity.ok(soilDataService.getSoilDataByType(soilType));
        }
        if (minMoisture != null && maxMoisture != null) {
            return ResponseEntity.ok(soilDataService.getSoilDataByMoistureRange(minMoisture, maxMoisture));
        }
        if (landId != null) {
            return ResponseEntity.ok(soilDataService.getSoilDataByLandId(landId));
        }
        if (startDate != null && endDate != null) {
            return ResponseEntity.ok(soilDataService.getSoilDataByDateRange(startDate, endDate));
        }
        return ResponseEntity.ok(soilDataService.getAllSoilData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoilData> getSoilDataById(@PathVariable Long id) {
        return ResponseEntity.ok(soilDataService.getSoilDataById(id));
    }

    @PostMapping
    public ResponseEntity<SoilData> createSoilData(@Valid @RequestBody SoilData soilData) {
        return ResponseEntity.ok(soilDataService.createSoilData(soilData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoilData> updateSoilData(@PathVariable Long id, @Valid @RequestBody SoilData details) {
        return ResponseEntity.ok(soilDataService.updateSoilData(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoilData(@PathVariable Long id) {
        soilDataService.deleteSoilData(id);
        return ResponseEntity.ok().build();
    }
}
