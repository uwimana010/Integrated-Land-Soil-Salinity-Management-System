package com.land.service;

import com.land.dto.LandRequest;
import com.land.dto.LandResponse;
import com.land.model.Land;
import com.land.model.User;
import com.land.repository.LandRepository;
import com.land.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LandService {

    private final LandRepository landRepository;
    private final UserRepository userRepository;

    public LandResponse createLand(LandRequest request) {
        User owner = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        Land land = Land.builder()
                .location(request.getLocation())
                .size(request.getSize())
                .landType(request.getLandType())
                .user(owner)
                .build();

        return toResponse(landRepository.save(land));
    }

    public List<LandResponse> getAllLands() {
        return landRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public LandResponse getLandById(Long id) {
        Land land = landRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Land not found with ID: " + id));
        return toResponse(land);
    }

    public List<LandResponse> getLandsByUserId(Long userId) {
        return landRepository.findByUser_UserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public LandResponse updateLand(Long id, LandRequest request) {
        Land land = landRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Land not found with ID: " + id));

        User owner = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        land.setLocation(request.getLocation());
        land.setSize(request.getSize());
        land.setLandType(request.getLandType());
        land.setUser(owner);

        return toResponse(landRepository.save(land));
    }

    public void deleteLand(Long id) {
        if (!landRepository.existsById(id)) {
            throw new RuntimeException("Land not found with ID: " + id);
        }
        landRepository.deleteById(id);
    }

    private LandResponse toResponse(Land land) {
        return LandResponse.builder()
                .landId(land.getLandId())
                .location(land.getLocation())
                .size(land.getSize())
                .landType(land.getLandType())
                .userId(land.getUser() != null ? land.getUser().getUserId() : null)
                .ownerName(land.getUser() != null ? land.getUser().getFullName() : null)
                .build();
    }
}
