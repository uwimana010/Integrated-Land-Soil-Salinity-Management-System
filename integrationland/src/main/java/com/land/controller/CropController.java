package com.land.controller;

import com.land.model.Crop;
import com.land.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    @GetMapping
    public ResponseEntity<List<Crop>> getAllCrops(
            @RequestParam(required = false) String salinityTolerance,
            @RequestParam(required = false) String soilRequirement) {
        if (salinityTolerance != null && !salinityTolerance.isEmpty()) {
            return ResponseEntity.ok(cropService.getCropsBySalinityTolerance(salinityTolerance));
        }
        if (soilRequirement != null && !soilRequirement.isEmpty()) {
            return ResponseEntity.ok(cropService.getCropsBySoilRequirement(soilRequirement));
        }
        return ResponseEntity.ok(cropService.getAllCrops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crop> getCropById(@PathVariable Long id) {
        return ResponseEntity.ok(cropService.getCropById(id));
    }

    @PostMapping
    public ResponseEntity<Crop> createCrop(@Valid @RequestBody Crop crop) {
        return ResponseEntity.ok(cropService.createCrop(crop));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crop> updateCrop(@PathVariable Long id, @Valid @RequestBody Crop cropDetails) {
        return ResponseEntity.ok(cropService.updateCrop(id, cropDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return ResponseEntity.ok().build();
    }
}
