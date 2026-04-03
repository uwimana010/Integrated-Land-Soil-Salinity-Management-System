package com.land.service;

import com.land.model.Crop;
import com.land.model.Recommendation;
import com.land.model.SoilData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationEngineService {

    private final SoilDataService soilDataService;
    private final CropService cropService;
    private final RecommendationService recommendationService;

    /**
     * Advanced matching logic using weighted scoring for pH, Salinity, and NPK nutrients.
     */
    public String generateRecommendationsForSoil(Long soilId) {
        SoilData soil = soilDataService.getSoilDataById(soilId);
        List<Crop> allCrops = cropService.getAllCrops();
        
        int generatedCount = 0;
        
        for (Crop crop : allCrops) {
            double score = calculateMatchScore(soil, crop);
            
            // If match score is high enough (e.g., above 70%), generate recommendation
            if (score >= 0.70) {
                String details = String.format(
                    "Recommended crop: %s. Match Quality: %.1f%%. This recommendation is based on a matching soil type (%s), " +
                    "compatible pH level (%.1f), and nutrient availability (N:%.1f, P:%.1f, K:%.1f).",
                    crop.getCropName(), score * 100, soil.getSoilType(), soil.getPhLevel(),
                    soil.getNitrogenLevel(), soil.getPhosphorusLevel(), soil.getPotassiumLevel()
                );

                Recommendation rec = Recommendation.builder()
                        .recommendationDetails(details)
                        .recommendationDate(LocalDate.now())
                        .soilData(soil)
                        .crop(crop)
                        .user(soil.getLand().getUser())
                        .build();
                        
                recommendationService.createRecommendation(rec);
                generatedCount++;
            }
        }
        
        return "Generated " + generatedCount + " high-quality recommendations for Soil Data ID " + soilId;
    }

    private double calculateMatchScore(SoilData soil, Crop crop) {
        double score = 0.0;
        double totalWeight = 0.0;

        // 1. Soil Type Match (Critical - Weight 0.4)
        totalWeight += 0.4;
        if (crop.getSoilRequirement().equalsIgnoreCase(soil.getSoilType())) {
            score += 0.4;
        }

        // 2. pH Level Match (Important - Weight 0.2)
        totalWeight += 0.2;
        if (soil.getPhLevel() != null && crop.getMinPh() != null && crop.getMaxPh() != null) {
            if (soil.getPhLevel() >= crop.getMinPh() && soil.getPhLevel() <= crop.getMaxPh()) {
                score += 0.2;
            } else if (Math.abs(soil.getPhLevel() - crop.getMinPh()) < 0.5 || Math.abs(soil.getPhLevel() - crop.getMaxPh()) < 0.5) {
                score += 0.1; // Partial match
            }
        }

        // 3. Salinity Match (Important - Weight 0.2)
        totalWeight += 0.2;
        if (isSalinityCompatible(soil.getSalinityLevel(), crop.getSalinityTolerance())) {
            score += 0.2;
        }

        // 4. Nutrient Match (Bonus - Weight 0.2)
        totalWeight += 0.2;
        double nutrientScore = 0.0;
        if (soil.getNitrogenLevel() != null && crop.getNitrogenRequirement() != null) {
            if (soil.getNitrogenLevel() >= crop.getNitrogenRequirement()) nutrientScore += 0.06;
        }
        if (soil.getPhosphorusLevel() != null && crop.getPhosphorusRequirement() != null) {
            if (soil.getPhosphorusLevel() >= crop.getPhosphorusRequirement()) nutrientScore += 0.07;
        }
        if (soil.getPotassiumLevel() != null && crop.getPotassiumRequirement() != null) {
            if (soil.getPotassiumLevel() >= crop.getPotassiumRequirement()) nutrientScore += 0.07;
        }
        score += nutrientScore;

        return score / totalWeight;
    }

    private boolean isSalinityCompatible(Float salinityLevel, String tolerance) {
        if (salinityLevel == null) return false;
        if ("HIGH".equalsIgnoreCase(tolerance)) return true; // High tolerance can handle anything
        if ("MEDIUM".equalsIgnoreCase(tolerance)) return salinityLevel < 8.0f;
        if ("LOW".equalsIgnoreCase(tolerance)) return salinityLevel < 4.0f;
        return false;
    }
}
