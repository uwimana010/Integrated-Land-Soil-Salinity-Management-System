package com.land.config;

import com.land.model.Crop;
import com.land.repository.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CropRepository cropRepository;

    @Override
    public void run(String... args) throws Exception {
        if (cropRepository.count() == 0) {
            List<Crop> defaultCrops = Arrays.asList(
                Crop.builder()
                    .cropName("Barley")
                    .salinityTolerance("High (12 dS/m)")
                    .soilRequirement("Well-drained loamy soil")
                    .build(),
                Crop.builder()
                    .cropName("Sugar Beet")
                    .salinityTolerance("High (7.0 - 12.0 dS/m)")
                    .soilRequirement("Deep, fertile loamy soil")
                    .build(),
                Crop.builder()
                    .cropName("Wheat")
                    .salinityTolerance("Moderate (6.0 dS/m)")
                    .soilRequirement("Silt loam or clay loam")
                    .build(),
                Crop.builder()
                    .cropName("Cotton")
                    .salinityTolerance("High (7.7 dS/m)")
                    .soilRequirement("Deep, well-drained alluvial soil")
                    .build(),
                Crop.builder()
                    .cropName("Rice")
                    .salinityTolerance("Moderate (3.0 dS/m)")
                    .soilRequirement("Heavy clay or loamy soil with hardpan")
                    .build(),
                Crop.builder()
                    .cropName("Beans")
                    .salinityTolerance("Low (0.8 dS/m)")
                    .soilRequirement("Rich, loamy soil with good drainage")
                    .build(),
                Crop.builder()
                    .cropName("Onion")
                    .salinityTolerance("Low (1.2 dS/m)")
                    .soilRequirement("Sandy loam or clay loam")
                    .build()
            );
            cropRepository.saveAll(defaultCrops);
            System.out.println(">>> SEEDED DEFAULT CROP DATA <<<");
        }
    }
}
