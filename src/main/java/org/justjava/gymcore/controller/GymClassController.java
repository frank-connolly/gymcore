package org.justjava.gymcore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.justjava.gymcore.model.GymClass;
import org.justjava.gymcore.service.GymClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/classes")
public class GymClassController {

    private final GymClassService gymClassService;

    @PostMapping
    public ResponseEntity<GymClass> createGymClass(@RequestBody GymClass gymClass) {
        log.info("Received request to create a gym class");
        GymClass created = gymClassService.createGymClass(gymClass);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GymClass> getGymClass(@PathVariable Long id) {
        log.info("Received request to fetch gym class with ID: {}", id);
        return gymClassService.getGymClass(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<GymClass> getAllGymClasses() {
        log.info("Received request to fetch all gym classes");
        return gymClassService.getAllGymClasses();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GymClass> updateGymClass(@PathVariable Long id, @RequestBody GymClass gymClass) {
        log.info("Received request to update gym class with ID: {}", id);
        try {
            GymClass updated = gymClassService.updateGymClass(id, gymClass);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("Error while updating gym class with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGymClass(@PathVariable Long id) {
        log.info("Received request to delete gym class with ID: {}", id);
        try {
            gymClassService.deleteGymClass(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error while deleting gym class with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
