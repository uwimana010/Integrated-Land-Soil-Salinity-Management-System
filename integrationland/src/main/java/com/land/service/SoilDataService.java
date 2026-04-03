package com.land.service;

import com.land.model.SoilData;
import com.land.repository.SoilDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoilDataService {

    private final SoilDataRepository soilDataRepository;

    public List<SoilData> getAllSoilData() {
        return soilDataRepository.findAll();
    }

    public List<SoilData> getSoilDataByType(String soilType) {
        return soilDataRepository.findBySoilType(soilType);
    }

    public List<SoilData> getSoilDataByMoistureRange(Float min, Float max) {
        return soilDataRepository.findByMoistureLevelBetween(min, max);
    }

    public List<SoilData> getSoilDataByLandId(Long landId) {
        return soilDataRepository.findByLand_LandId(landId);
    }

    public List<SoilData> getSoilDataByDateRange(java.time.LocalDate start, java.time.LocalDate end) {
        return soilDataRepository.findByRecordDateBetween(start, end);
    }

    public SoilData getSoilDataById(Long id) {
        return soilDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SoilData not found with id " + id));
    }

    public SoilData createSoilData(SoilData soilData) {
        return soilDataRepository.save(soilData);
    }

    public SoilData updateSoilData(Long id, SoilData details) {
        SoilData soilData = getSoilDataById(id);
        soilData.setSoilType(details.getSoilType());
        soilData.setMoistureLevel(details.getMoistureLevel());
        soilData.setNutrientLevel(details.getNutrientLevel());
        soilData.setSalinityLevel(details.getSalinityLevel());
        soilData.setRecordDate(details.getRecordDate());
        return soilDataRepository.save(soilData);
    }

    public void deleteSoilData(Long id) {
        SoilData soilData = getSoilDataById(id);
        soilDataRepository.delete(soilData);
    }
}
