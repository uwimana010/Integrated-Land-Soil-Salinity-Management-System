package com.land.service;

import com.land.dto.CropRequest;
import com.land.dto.CropResponse;
import com.land.model.Crop;
import com.land.repository.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CropService {

    private final CropRepository cropRepository;

    public CropResponse createCrop(CropRequest request) {
        if (cropRepository.findByCropName(request.getCropName()).isPresent()) {
            throw new RuntimeException("Crop already exists: " + request.getCropName());
        }

        Crop crop = Crop.builder()
                .cropName(request.getCropName())
                .salinityTolerance(request.getSalinityTolerance())
                .soilRequirement(request.getSoilRequirement())
                .build();

        return toResponse(cropRepository.save(crop));
    }

    public List<CropResponse> getAllCrops() {
        return cropRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CropResponse getCropById(Long id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found with ID: " + id));
        return toResponse(crop);
    }

    public CropResponse updateCrop(Long id, CropRequest request) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found with ID: " + id));

        crop.setCropName(request.getCropName());
        crop.setSalinityTolerance(request.getSalinityTolerance());
        crop.setSoilRequirement(request.getSoilRequirement());

        return toResponse(cropRepository.save(crop));
    }

    public void deleteCrop(Long id) {
        if (!cropRepository.existsById(id)) {
            throw new RuntimeException("Crop not found with ID: " + id);
        }
        cropRepository.deleteById(id);
    }

    private CropResponse toResponse(Crop crop) {
        return CropResponse.builder()
                .cropId(crop.getCropId())
                .cropName(crop.getCropName())
                .salinityTolerance(crop.getSalinityTolerance())
                .soilRequirement(crop.getSoilRequirement())
                .build();
    }
}
