package com.land.controller;

import com.land.dto.ApiResponse;
import com.land.dto.RecommendationRequest;
import com.land.dto.RecommendationResponse;
import com.land.service.RecommendationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * Auto-generate recommendation from a soil record.
     * POST /api/recommendations/generate?soilId=1&officerId=2
     */
    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<RecommendationResponse>> generate(
            @RequestParam Long soilId,
            @RequestParam Long officerId) {
        RecommendationResponse response = recommendationService.generateRecommendation(soilId, officerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Recommendation generated successfully", response));
    }

    /**
     * Manually create a recommendation.
     * POST /api/recommendations
     */
    @PostMapping
    public ResponseEntity<ApiResponse<RecommendationResponse>> createRecommendation(
            @Valid @RequestBody RecommendationRequest request) {
        RecommendationResponse response = recommendationService.createRecommendation(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Recommendation created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RecommendationResponse>>> getAllRecommendations() {
        return ResponseEntity.ok(ApiResponse.success(recommendationService.getAllRecommendations()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecommendationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(recommendationService.getRecommendationById(id)));
    }

    @GetMapping("/soil/{soilId}")
    public ResponseEntity<ApiResponse<List<RecommendationResponse>>> getBySoilId(
            @PathVariable Long soilId) {
        return ResponseEntity.ok(ApiResponse.success(
                recommendationService.getRecommendationsBySoilId(soilId)));
    }

    @GetMapping("/officer/{userId}")
    public ResponseEntity<ApiResponse<List<RecommendationResponse>>> getByOfficer(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(
                recommendationService.getRecommendationsByOfficer(userId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RecommendationResponse>> updateRecommendation(
            @PathVariable Long id, @Valid @RequestBody RecommendationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Recommendation updated",
                recommendationService.updateRecommendation(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRecommendation(@PathVariable Long id) {
        recommendationService.deleteRecommendation(id);
        return ResponseEntity.ok(ApiResponse.success("Recommendation deleted", null));
    }
}
