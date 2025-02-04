package org.justjava.gymcore.controller;

import org.justjava.gymcore.model.GymClass;
import org.justjava.gymcore.service.GymClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class GymClassController {

    private final GymClassService gymClassService;
    public GymClassController(GymClassService gymClassService) {
        this.gymClassService = gymClassService;
    }

    @PostMapping
    public ResponseEntity<GymClass> createGymClass(@RequestBody GymClass gymClass) {
        GymClass created = gymClassService.createGymClass(gymClass);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GymClass> getGymClass(@PathVariable Long id) {
        return gymClassService.getGymClass(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<GymClass> getAllGymClasses() {
        return gymClassService.getAllGymClasses();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GymClass> updateGymClass(@PathVariable Long id, @RequestBody GymClass gymClass) {
        GymClass updated = gymClassService.updateGymClass(id, gymClass);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGymClass(@PathVariable Long id) {
        gymClassService.deleteGymClass(id);
        return ResponseEntity.noContent().build();
    }
}
