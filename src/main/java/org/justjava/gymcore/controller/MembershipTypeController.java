package org.justjava.gymcore.controller;


import lombok.RequiredArgsConstructor;
import org.justjava.gymcore.model.MembershipType;
import org.justjava.gymcore.repository.MembershipTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/membership-types")
public class MembershipTypeController {

    private final MembershipTypeRepository repository;

    @PostMapping
    public ResponseEntity<MembershipType> createMembershipType(@RequestBody MembershipType membershipType) {
        MembershipType saved = repository.save(membershipType);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipType> getMembershipType(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MembershipType>> getAllMembershipTypes() {
        List<MembershipType> membershipTypes = repository.findAll();
        return ResponseEntity.ok(membershipTypes);
    }

}
