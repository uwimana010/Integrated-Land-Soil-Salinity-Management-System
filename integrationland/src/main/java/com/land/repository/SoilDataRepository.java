package com.land.repository;

import com.land.model.SoilData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoilDataRepository extends JpaRepository<SoilData, Long> {
    List<SoilData> findByLand_LandId(Long landId);
    List<SoilData> findBySalinityLevelGreaterThan(Float threshold);

    @Query("SELECT s FROM SoilData s ORDER BY s.recordDate DESC")
    List<SoilData> findAllOrderByRecordDateDesc();
}
