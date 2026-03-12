package com.land.repository;

import com.land.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findBySoilData_SoilId(Long soilId);
    List<Recommendation> findByUser_UserId(Long userId);
    List<Recommendation> findByCrop_CropId(Long cropId);
}
