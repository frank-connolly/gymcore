package org.justjava.gymcore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.justjava.gymcore.model.Booking;
import org.justjava.gymcore.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        log.info("Creating new booking for user: {}", booking.getUser());
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBooking(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        checkIfBookingExists(id);
        bookingDetails.setId(id);
        return bookingRepository.save(bookingDetails);
    }

    public void deleteBooking(Long id) {
        checkIfBookingExists(id);
        bookingRepository.deleteById(id);
    }

    private void checkIfBookingExists(Long id) {
        if (!bookingRepository.existsById(id)) {
            log.warn("Cannot proceed - Booking ID {} not found", id);
            throw new IllegalArgumentException("Booking not found");
        }
    }
}
