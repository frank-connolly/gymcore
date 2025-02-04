package org.justjava.gymcore.repository;

import org.justjava.gymcore.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {}
