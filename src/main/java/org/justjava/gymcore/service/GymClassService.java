package org.justjava.gymcore.service;

import org.justjava.gymcore.model.GymClass;
import org.justjava.gymcore.repository.GymClassRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GymClassService {

    private final GymClassRepository gymClassRepository;
    public GymClassService(GymClassRepository gymClassRepository) {
        this.gymClassRepository = gymClassRepository;
    }

    public GymClass createGymClass(GymClass gymClass) {
        return gymClassRepository.save(gymClass);
    }

    public Optional<GymClass> getGymClass(Long id) {
        return gymClassRepository.findById(id);
    }

    public List<GymClass> getAllGymClasses() {
        return gymClassRepository.findAll();
    }

    public GymClass updateGymClass(Long id, GymClass gymClassDetails) {
        gymClassDetails.setId(id);
        return gymClassRepository.save(gymClassDetails);
    }

    public void deleteGymClass(Long id) {
        gymClassRepository.deleteById(id);
    }
}
