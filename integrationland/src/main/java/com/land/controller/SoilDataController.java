package com.land.controller;

import com.land.dto.ApiResponse;
import com.land.dto.SoilDataRequest;
import com.land.dto.SoilDataResponse;
import com.land.service.SoilDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soil-data")
@RequiredArgsConstructor
public class SoilDataController {

    private final SoilDataService soilDataService;

    @PostMapping
    public ResponseEntity<ApiResponse<SoilDataResponse>> createSoilData(
            @Valid @RequestBody SoilDataRequest request) {
        SoilDataResponse response = soilDataService.createSoilData(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Soil record created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SoilDataResponse>>> getAllSoilData() {
        return ResponseEntity.ok(ApiResponse.success(soilDataService.getAllSoilData()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SoilDataResponse>> getSoilDataById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(soilDataService.getSoilDataById(id)));
    }

    @GetMapping("/land/{landId}")
    public ResponseEntity<ApiResponse<List<SoilDataResponse>>> getSoilDataByLand(
            @PathVariable Long landId) {
        return ResponseEntity.ok(ApiResponse.success(soilDataService.getSoilDataByLandId(landId)));
    }

    @GetMapping("/high-salinity")
    public ResponseEntity<ApiResponse<List<SoilDataResponse>>> getHighSalinityRecords(
            @RequestParam(defaultValue = "4.0") Float threshold) {
        return ResponseEntity.ok(ApiResponse.success(
                soilDataService.getHighSalinityRecords(threshold)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SoilDataResponse>> updateSoilData(
            @PathVariable Long id, @Valid @RequestBody SoilDataRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Soil record updated", soilDataService.updateSoilData(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSoilData(@PathVariable Long id) {
        soilDataService.deleteSoilData(id);
        return ResponseEntity.ok(ApiResponse.success("Soil record deleted", null));
    }
}
