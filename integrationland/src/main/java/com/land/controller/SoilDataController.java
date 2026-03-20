package com.land.controller;

import com.land.model.SoilData;
import com.land.service.SoilDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soil-data")
@RequiredArgsConstructor
public class SoilDataController {

    private final SoilDataService soilDataService;

    @GetMapping
    public ResponseEntity<List<SoilData>> getAllSoilData() {
        return ResponseEntity.ok(soilDataService.getAllSoilData());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoilData> getSoilDataById(@PathVariable Long id) {
        return ResponseEntity.ok(soilDataService.getSoilDataById(id));
    }

    @PostMapping
    public ResponseEntity<SoilData> createSoilData(@RequestBody SoilData soilData) {
        return ResponseEntity.ok(soilDataService.createSoilData(soilData));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoilData> updateSoilData(@PathVariable Long id, @RequestBody SoilData details) {
        return ResponseEntity.ok(soilDataService.updateSoilData(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoilData(@PathVariable Long id) {
        soilDataService.deleteSoilData(id);
        return ResponseEntity.ok().build();
    }
}
