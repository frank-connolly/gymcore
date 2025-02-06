package org.justjava.gymcore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.justjava.gymcore.model.GymClass;
import org.justjava.gymcore.repository.GymClassRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GymClassService {

    private final GymClassRepository gymClassRepository;

    public GymClass createGymClass(GymClass gymClass) {
        log.info("Creating new gym class.");
        return gymClassRepository.save(gymClass);
    }

    public Optional<GymClass> getGymClass(Long id) {
        return gymClassRepository.findById(id);
    }

    public List<GymClass> getAllGymClasses() {
        return gymClassRepository.findAll();
    }

    public GymClass updateGymClass(Long id, GymClass gymClassDetails) {
        checkIfGymClassExists(id);
        gymClassDetails.setId(id);
        return gymClassRepository.save(gymClassDetails);
    }

    public void deleteGymClass(Long id) {
        checkIfGymClassExists(id);
        gymClassRepository.deleteById(id);
    }

    private void checkIfGymClassExists(Long id) {
        if (!gymClassRepository.existsById(id)) {
            log.warn("Cannot proceed - Gym class ID {} not found", id);
            throw new IllegalArgumentException("Gym class not found");
        }
    }
}
