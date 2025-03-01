package org.justjava.gymcore.repository;

import org.justjava.gymcore.model.Booking;
import org.justjava.gymcore.model.GymClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Count confirmed bookings for a class
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.gymClass.id = :gymClassId")
    long countByGymClassId(@Param("gymClassId") Long gymClassId);
}
