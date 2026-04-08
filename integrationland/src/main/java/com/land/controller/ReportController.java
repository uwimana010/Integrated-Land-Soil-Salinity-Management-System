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

    @GetMapping("/detailed")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OFFICER')")
    public ResponseEntity<Map<String, Object>> getDetailedReport() {
        Map<String, Object> data = new HashMap<>();
        
        // Use generic Object maps for categorization
        data.put("landTypeDistribution", landRepository.findAll().stream()
                .collect(java.util.stream.Collectors.groupingBy(com.land.model.Land::getLandType, java.util.stream.Collectors.counting())));
        
        data.put("soilTypeDistribution", soilDataRepository.findAll().stream()
                .collect(java.util.stream.Collectors.groupingBy(com.land.model.SoilData::getSoilType, java.util.stream.Collectors.counting())));
                
        // Average moisture by soil type
        data.put("avgMoisture", soilDataRepository.findAll().stream()
                .collect(java.util.stream.Collectors.groupingBy(com.land.model.SoilData::getSoilType, 
                        java.util.stream.Collectors.averagingDouble(com.land.model.SoilData::getMoistureLevel))));

        // Recent records
        data.put("recentRecords", soilDataRepository.findAll().stream()
                .sorted((a, b) -> b.getSoilId().compareTo(a.getSoilId()))
                .limit(10)
                .collect(java.util.stream.Collectors.toList()));
                
        return ResponseEntity.ok(data);
    }
}
