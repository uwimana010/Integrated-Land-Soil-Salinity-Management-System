package com.land.repository;

import com.land.model.SoilData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoilDataRepository extends JpaRepository<SoilData, Long> {
    List<SoilData> findBySoilType(String soilType);
    List<SoilData> findByMoistureLevelBetween(Float min, Float max);
    List<SoilData> findByLand_LandId(Long landId);
    List<SoilData> findByRecordDateBetween(java.time.LocalDate start, java.time.LocalDate end);
}
