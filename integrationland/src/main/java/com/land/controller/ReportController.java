package com.land.controller;

import com.land.repository.LandRepository;
import com.land.repository.RecommendationRepository;
import com.land.repository.SoilDataRepository;
import com.land.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final LandRepository landRepository;
    private final SoilDataRepository soilDataRepository;
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;

    @GetMapping("/summary")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OFFICER')")
    public ResponseEntity<Map<String, Long>> getSummaryReport() {
        Map<String, Long> summary = new HashMap<>();
        summary.put("totalLands", landRepository.count());
        summary.put("totalSoilRecords", soilDataRepository.count());
        summary.put("totalRecommendations", recommendationRepository.count());
        summary.put("totalUsers", userRepository.count());
        return ResponseEntity.ok(summary);
    }
}
