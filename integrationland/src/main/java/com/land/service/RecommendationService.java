package com.land.service;

import com.land.dto.RecommendationRequest;
import com.land.dto.RecommendationResponse;
import com.land.model.Crop;
import com.land.model.Recommendation;
import com.land.model.SoilData;
import com.land.model.User;
import com.land.repository.CropRepository;
import com.land.repository.RecommendationRepository;
import com.land.repository.SoilDataRepository;
import com.land.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final SoilDataRepository soilDataRepository;
    private final CropRepository cropRepository;
    private final UserRepository userRepository;

    // ──────────────────────────────────────────────
    // Auto-generate a recommendation from soil data
    // ──────────────────────────────────────────────
    public RecommendationResponse generateRecommendation(Long soilId, Long officerUserId) {
        SoilData soilData = soilDataRepository.findById(soilId)
                .orElseThrow(() -> new RuntimeException("Soil record not found with ID: " + soilId));

        User officer = userRepository.findById(officerUserId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + officerUserId));

        String category = SoilDataService.categorizeSalinity(soilData.getSalinityLevel());
        String details = buildRecommendationText(soilData, category);

        // Try to match a suitable crop by salinity tolerance keyword
        Crop suggestedCrop = findSuitableCrop(category);

        Recommendation rec = Recommendation.builder()
                .recommendationDetails(details)
                .recommendationDate(LocalDate.now())
                .soilData(soilData)
                .crop(suggestedCrop)
                .user(officer)
                .build();

        return toResponse(recommendationRepository.save(rec));
    }

    // ──────────────────────────────────────────────
    // Manual creation by officer
    // ──────────────────────────────────────────────
    public RecommendationResponse createRecommendation(RecommendationRequest request) {
        SoilData soilData = soilDataRepository.findById(request.getSoilId())
                .orElseThrow(() -> new RuntimeException("Soil record not found with ID: " + request.getSoilId()));

        User officer = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        Crop crop = null;
        if (request.getCropId() != null) {
            crop = cropRepository.findById(request.getCropId())
                    .orElseThrow(() -> new RuntimeException("Crop not found with ID: " + request.getCropId()));
        }

        Recommendation rec = Recommendation.builder()
                .recommendationDetails(request.getRecommendationDetails())
                .recommendationDate(request.getRecommendationDate() != null
                        ? request.getRecommendationDate() : LocalDate.now())
                .soilData(soilData)
                .crop(crop)
                .user(officer)
                .build();

        return toResponse(recommendationRepository.save(rec));
    }

    public List<RecommendationResponse> getAllRecommendations() {
        return recommendationRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RecommendationResponse getRecommendationById(Long id) {
        Recommendation rec = recommendationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommendation not found with ID: " + id));
        return toResponse(rec);
    }

    public List<RecommendationResponse> getRecommendationsBySoilId(Long soilId) {
        return recommendationRepository.findBySoilData_SoilId(soilId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RecommendationResponse> getRecommendationsByOfficer(Long userId) {
        return recommendationRepository.findByUser_UserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RecommendationResponse updateRecommendation(Long id, RecommendationRequest request) {
        Recommendation rec = recommendationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommendation not found with ID: " + id));

        SoilData soilData = soilDataRepository.findById(request.getSoilId())
                .orElseThrow(() -> new RuntimeException("Soil record not found with ID: " + request.getSoilId()));

        User officer = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        Crop crop = null;
        if (request.getCropId() != null) {
            crop = cropRepository.findById(request.getCropId())
                    .orElseThrow(() -> new RuntimeException("Crop not found with ID: " + request.getCropId()));
        }

        rec.setRecommendationDetails(request.getRecommendationDetails());
        rec.setRecommendationDate(request.getRecommendationDate() != null
                ? request.getRecommendationDate() : rec.getRecommendationDate());
        rec.setSoilData(soilData);
        rec.setCrop(crop);
        rec.setUser(officer);

        return toResponse(recommendationRepository.save(rec));
    }

    public void deleteRecommendation(Long id) {
        if (!recommendationRepository.existsById(id)) {
            throw new RuntimeException("Recommendation not found with ID: " + id);
        }
        recommendationRepository.deleteById(id);
    }

    // ──────────────────────────────────────────────
    // Private helpers
    // ──────────────────────────────────────────────
    private String buildRecommendationText(SoilData s, String category) {
        return switch (category) {
            case "LOW" -> String.format(
                    "Salinity level %.2f dS/m (LOW). Soil conditions are favourable. " +
                    "Most crops can be cultivated. Maintain current irrigation practices " +
                    "and monitor nutrient levels regularly. Soil type: %s.",
                    s.getSalinityLevel(), s.getSoilType());
            case "MODERATE" -> String.format(
                    "Salinity level %.2f dS/m (MODERATE). Apply leaching irrigation to " +
                    "reduce salt accumulation. Select moderately salt-tolerant crops such as " +
                    "wheat, barley, or cotton. Avoid over-irrigation. Soil type: %s.",
                    s.getSalinityLevel(), s.getSoilType());
            case "HIGH" -> String.format(
                    "Salinity level %.2f dS/m (HIGH). Immediate soil reclamation is advised. " +
                    "Apply gypsum and deep leaching irrigation. Only highly tolerant crops " +
                    "such as barley, sugarbeet, or date palm are suitable. Soil type: %s.",
                    s.getSalinityLevel(), s.getSoilType());
            case "VERY_HIGH" -> String.format(
                    "Salinity level %.2f dS/m (VERY HIGH). Land requires extensive reclamation " +
                    "before any crop cultivation. Recommended actions: install subsurface drainage, " +
                    "apply heavy gypsum treatment, perform repeated leaching cycles. " +
                    "Soil type: %s. Crop cultivation is not currently advised.",
                    s.getSalinityLevel(), s.getSoilType());
            default -> "Salinity data unavailable. Please re-enter soil measurements.";
        };
    }

    private Crop findSuitableCrop(String salinityCategory) {
        // Map salinity category to a known salinity tolerance keyword
        String toleranceKeyword = switch (salinityCategory) {
            case "LOW" -> "low";
            case "MODERATE" -> "moderate";
            case "HIGH" -> "high";
            case "VERY_HIGH" -> "very high";
            default -> null;
        };

        if (toleranceKeyword == null) return null;

        return cropRepository.findAll().stream()
                .filter(c -> c.getSalinityTolerance() != null &&
                        c.getSalinityTolerance().toLowerCase().contains(toleranceKeyword))
                .findFirst()
                .orElse(null);
    }

    private RecommendationResponse toResponse(Recommendation r) {
        return RecommendationResponse.builder()
                .recommendationId(r.getRecommendationId())
                .recommendationDetails(r.getRecommendationDetails())
                .recommendationDate(r.getRecommendationDate())
                .soilId(r.getSoilData() != null ? r.getSoilData().getSoilId() : null)
                .salinityLevel(r.getSoilData() != null ? r.getSoilData().getSalinityLevel() : null)
                .cropId(r.getCrop() != null ? r.getCrop().getCropId() : null)
                .cropName(r.getCrop() != null ? r.getCrop().getCropName() : null)
                .userId(r.getUser() != null ? r.getUser().getUserId() : null)
                .officerName(r.getUser() != null ? r.getUser().getFullName() : null)
                .build();
    }
}
