package com.land.controller;

import com.land.dto.ApiResponse;
import com.land.dto.CropRequest;
import com.land.dto.CropResponse;
import com.land.service.CropService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    @PostMapping
    public ResponseEntity<ApiResponse<CropResponse>> createCrop(@Valid @RequestBody CropRequest request) {
        CropResponse response = cropService.createCrop(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Crop added successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CropResponse>>> getAllCrops() {
        return ResponseEntity.ok(ApiResponse.success(cropService.getAllCrops()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CropResponse>> getCropById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(cropService.getCropById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CropResponse>> updateCrop(
            @PathVariable Long id, @Valid @RequestBody CropRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Crop updated successfully", cropService.updateCrop(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCrop(@PathVariable Long id) {
        cropService.deleteCrop(id);
        return ResponseEntity.ok(ApiResponse.success("Crop deleted successfully", null));
    }
}
