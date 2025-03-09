package org.justjava.gymcore.repository;

import org.justjava.gymcore.model.Waitlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WaitlistRepository extends JpaRepository<Waitlist, Long> {
    List<Waitlist> findByGymClassId(Long gymClassId);
    boolean existsByUserIdAndGymClassId(Long userId, Long gymClassId);
    void deleteByUserIdAndGymClassId(Long userId, Long gymClassId);


}
