package com.land.controller;

import com.land.model.Land;
import com.land.service.LandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lands")
@RequiredArgsConstructor
public class LandController {

    private final LandService landService;

    @GetMapping
    public ResponseEntity<List<Land>> getAllLands() {
        return ResponseEntity.ok(landService.getAllLands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Land> getLandById(@PathVariable Long id) {
        return ResponseEntity.ok(landService.getLandById(id));
    }

    @PostMapping
    public ResponseEntity<Land> createLand(@RequestBody Land land) {
        return ResponseEntity.ok(landService.createLand(land));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Land> updateLand(@PathVariable Long id, @RequestBody Land landDetails) {
        return ResponseEntity.ok(landService.updateLand(id, landDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLand(@PathVariable Long id) {
        landService.deleteLand(id);
        return ResponseEntity.ok().build();
    }
}
