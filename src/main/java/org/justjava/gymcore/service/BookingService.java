package org.justjava.gymcore.service;

import lombok.RequiredArgsConstructor;
import org.justjava.gymcore.model.Booking;
import org.justjava.gymcore.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBooking(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking updateBooking(Long id, Booking bookingDetails) {
        bookingDetails.setId(id);
        return bookingRepository.save(bookingDetails);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
