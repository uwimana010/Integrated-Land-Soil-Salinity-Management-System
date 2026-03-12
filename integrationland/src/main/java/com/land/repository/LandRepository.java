package com.land.repository;

import com.land.model.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandRepository extends JpaRepository<Land, Long> {
    List<Land> findByUser_UserId(Long userId);
    List<Land> findByLocationContainingIgnoreCase(String location);
    List<Land> findByLandType(String landType);
}
