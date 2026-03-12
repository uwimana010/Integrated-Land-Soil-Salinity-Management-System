package com.land.service;

import com.land.dto.SoilDataRequest;
import com.land.dto.SoilDataResponse;
import com.land.model.Land;
import com.land.model.SoilData;
import com.land.repository.LandRepository;
import com.land.repository.SoilDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SoilDataService {

    private final SoilDataRepository soilDataRepository;
    private final LandRepository landRepository;

    // Salinity thresholds in dS/m (electrical conductivity)
    // LOW     < 2    dS/m  – suitable for most crops
    // MODERATE  2–4  dS/m  – tolerant crops only
    // HIGH      4–8  dS/m  – only highly tolerant crops
    // VERY_HIGH > 8  dS/m  – requires reclamation before any crop cultivation
    public static String categorizeSalinity(Float salinityLevel) {
        if (salinityLevel == null) return "UNKNOWN";
        if (salinityLevel < 2.0f) return "LOW";
        if (salinityLevel < 4.0f) return "MODERATE";
        if (salinityLevel < 8.0f) return "HIGH";
        return "VERY_HIGH";
    }

    public SoilDataResponse createSoilData(SoilDataRequest request) {
        Land land = landRepository.findById(request.getLandId())
                .orElseThrow(() -> new RuntimeException("Land not found with ID: " + request.getLandId()));

        SoilData soilData = SoilData.builder()
                .soilType(request.getSoilType())
                .moistureLevel(request.getMoistureLevel())
                .nutrientLevel(request.getNutrientLevel())
                .salinityLevel(request.getSalinityLevel())
                .recordDate(request.getRecordDate() != null ? request.getRecordDate() : LocalDate.now())
                .land(land)
                .build();

        return toResponse(soilDataRepository.save(soilData));
    }

    public List<SoilDataResponse> getAllSoilData() {
        return soilDataRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SoilDataResponse getSoilDataById(Long id) {
        SoilData data = soilDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soil record not found with ID: " + id));
        return toResponse(data);
    }

    public List<SoilDataResponse> getSoilDataByLandId(Long landId) {
        return soilDataRepository.findByLand_LandId(landId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<SoilDataResponse> getHighSalinityRecords(Float threshold) {
        return soilDataRepository.findBySalinityLevelGreaterThan(threshold).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public SoilDataResponse updateSoilData(Long id, SoilDataRequest request) {
        SoilData soilData = soilDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Soil record not found with ID: " + id));

        Land land = landRepository.findById(request.getLandId())
                .orElseThrow(() -> new RuntimeException("Land not found with ID: " + request.getLandId()));

        soilData.setSoilType(request.getSoilType());
        soilData.setMoistureLevel(request.getMoistureLevel());
        soilData.setNutrientLevel(request.getNutrientLevel());
        soilData.setSalinityLevel(request.getSalinityLevel());
        soilData.setRecordDate(request.getRecordDate() != null ? request.getRecordDate() : soilData.getRecordDate());
        soilData.setLand(land);

        return toResponse(soilDataRepository.save(soilData));
    }

    public void deleteSoilData(Long id) {
        if (!soilDataRepository.existsById(id)) {
            throw new RuntimeException("Soil record not found with ID: " + id);
        }
        soilDataRepository.deleteById(id);
    }

    private SoilDataResponse toResponse(SoilData s) {
        return SoilDataResponse.builder()
                .soilId(s.getSoilId())
                .soilType(s.getSoilType())
                .moistureLevel(s.getMoistureLevel())
                .nutrientLevel(s.getNutrientLevel())
                .salinityLevel(s.getSalinityLevel())
                .recordDate(s.getRecordDate())
                .landId(s.getLand() != null ? s.getLand().getLandId() : null)
                .landLocation(s.getLand() != null ? s.getLand().getLocation() : null)
                .salinityCategory(categorizeSalinity(s.getSalinityLevel()))
                .build();
    }
}
