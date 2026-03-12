package com.land.controller;

import com.land.dto.ApiResponse;
import com.land.dto.LandRequest;
import com.land.dto.LandResponse;
import com.land.service.LandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lands")
@RequiredArgsConstructor
public class LandController {

    private final LandService landService;

    @PostMapping
    public ResponseEntity<ApiResponse<LandResponse>> createLand(@Valid @RequestBody LandRequest request) {
        LandResponse response = landService.createLand(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Land registered successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LandResponse>>> getAllLands() {
        return ResponseEntity.ok(ApiResponse.success(landService.getAllLands()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LandResponse>> getLandById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(landService.getLandById(id)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<LandResponse>>> getLandsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(landService.getLandsByUserId(userId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LandResponse>> updateLand(
            @PathVariable Long id, @Valid @RequestBody LandRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Land updated successfully", landService.updateLand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLand(@PathVariable Long id) {
        landService.deleteLand(id);
        return ResponseEntity.ok(ApiResponse.success("Land deleted successfully", null));
    }
}
