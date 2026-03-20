package com.land.service;

import com.land.model.Land;
import com.land.repository.LandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandService {

    private final LandRepository landRepository;

    public List<Land> getAllLands() {
        return landRepository.findAll();
    }

    public Land getLandById(Long id) {
        return landRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Land not found with id " + id));
    }

    public Land createLand(Land land) {
        return landRepository.save(land);
    }

    public Land updateLand(Long id, Land landDetails) {
        Land land = getLandById(id);
        land.setLocation(landDetails.getLocation());
        land.setSize(landDetails.getSize());
        land.setLandType(landDetails.getLandType());
        // For simplicity, we might not update User here directly unless passed
        return landRepository.save(land);
    }

    public void deleteLand(Long id) {
        Land land = getLandById(id);
        landRepository.delete(land);
    }
}
