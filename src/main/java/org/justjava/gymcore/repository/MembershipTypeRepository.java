package org.justjava.gymcore.repository;

import org.justjava.gymcore.model.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipTypeRepository extends JpaRepository<MembershipType, Long> {
}
