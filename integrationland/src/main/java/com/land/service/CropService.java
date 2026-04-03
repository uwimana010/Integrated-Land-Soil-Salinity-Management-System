package com.land.service;

import com.land.model.Crop;
import com.land.repository.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropService {

    private final CropRepository cropRepository;

    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    public List<Crop> getCropsBySalinityTolerance(String salinityTolerance) {
        return cropRepository.findBySalinityTolerance(salinityTolerance);
    }

    public List<Crop> getCropsBySoilRequirement(String soilRequirement) {
        return cropRepository.findBySoilRequirement(soilRequirement);
    }

    public Crop getCropById(Long id) {
        return cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found with id " + id));
    }

    public Crop createCrop(Crop crop) {
        return cropRepository.save(crop);
    }

    public Crop updateCrop(Long id, Crop details) {
        Crop crop = getCropById(id);
        crop.setCropName(details.getCropName());
        crop.setSalinityTolerance(details.getSalinityTolerance());
        crop.setSoilRequirement(details.getSoilRequirement());
        return cropRepository.save(crop);
    }

    public void deleteCrop(Long id) {
        Crop crop = getCropById(id);
        cropRepository.delete(crop);
    }
}
