package com.land.controller;

import com.land.model.Land;
import com.land.service.LandService;
import com.land.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/lands")
@RequiredArgsConstructor
public class LandController {

    private final LandService landService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Land>> getAllLands(@RequestParam(required = false) String landType) {
        if (landType != null && !landType.isEmpty()) {
            return ResponseEntity.ok(landService.getLandsByType(landType));
        }
        return ResponseEntity.ok(landService.getAllLands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Land> getLandById(@PathVariable Long id) {
        return ResponseEntity.ok(landService.getLandById(id));
    }

    @PostMapping
    public ResponseEntity<Land> createLand(@Valid @RequestBody Land land) {
        String email = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        com.land.model.User user = userService.findByEmail(email).orElse(null);
        land.setUser(user);
        return ResponseEntity.ok(landService.createLand(land));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Land> updateLand(@PathVariable Long id, @Valid @RequestBody Land landDetails) {
        return ResponseEntity.ok(landService.updateLand(id, landDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLand(@PathVariable Long id) {
        landService.deleteLand(id);
        return ResponseEntity.ok().build();
    }
}
