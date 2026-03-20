package com.land.service;

import com.land.model.Recommendation;
import com.land.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }

    public Recommendation getRecommendationById(Long id) {
        return recommendationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommendation not found with id " + id));
    }

    public Recommendation createRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public Recommendation updateRecommendation(Long id, Recommendation details) {
        Recommendation rec = getRecommendationById(id);
        rec.setRecommendationDetails(details.getRecommendationDetails());
        rec.setRecommendationDate(details.getRecommendationDate());
        return recommendationRepository.save(rec);
    }

    public void deleteRecommendation(Long id) {
        Recommendation rec = getRecommendationById(id);
        recommendationRepository.delete(rec);
    }
}
