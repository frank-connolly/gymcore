package org.justjava.gymcore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.justjava.gymcore.model.GymClass;
import org.justjava.gymcore.model.UserRole;
import org.justjava.gymcore.model.Booking;
import org.justjava.gymcore.model.User;
import org.justjava.gymcore.model.dto.ResponseDetail;
import org.justjava.gymcore.repository.BookingRepository;
import org.justjava.gymcore.repository.GymClassRepository;
import org.justjava.gymcore.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final GymClassRepository gymClassRepository;

    public ResponseEntity<?> createBooking(Booking booking) throws BadRequestException {
        log.info("Creating new booking for user: {}", booking.getUser());

        //Validation of users
        this.validateUsers(booking);

        this.validateGymClassTimePeriod(booking);

        if (this.checkTimePeriodForTrainer(booking)) {
            this.createGymClass(booking);
            Booking bookedEntity = bookingRepository.save(booking);
            return new ResponseEntity<>(bookedEntity, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new ResponseDetail("This trainer already has gym class on requested time period."), HttpStatus.BAD_REQUEST);
    }

    private boolean checkTimePeriodForTrainer(Booking booking) {
        LocalDateTime bookingStartTime = booking.getGymClass().getScheduledAt();
        LocalDateTime bookingEndTime = booking.getGymClass().getScheduleEnd();
        boolean isTrainerAvailable = this.gymClassRepository.existsByUserIdAndBookingTimePeriod(booking.getGymClass().getTrainer().getId(), bookingStartTime, bookingEndTime);
        return isTrainerAvailable;
    }

    private void validateGymClassTimePeriod(Booking booking) throws BadRequestException {
        if (booking.getGymClass().getScheduledAt().isAfter(booking.getGymClass().getScheduleEnd()))
            throw new BadRequestException("Time period is not valid.");
    }

    private void validateUsers(Booking booking) throws BadRequestException {
        User trainer = userRepository.findByUserIdAndUserRole(booking.getGymClass().getTrainer().getId(), UserRole.TRAINER);
        if (Objects.isNull(trainer))
            throw new BadRequestException("Trainer can not be found");

        User trainee = userRepository.findByUserIdAndUserRole(booking.getUser().getId(), UserRole.MEMBER);
        if (Objects.isNull(trainee))
            throw new BadRequestException("Member can not be found");
    }

    private void createGymClass(Booking booking) {
        GymClass gymClass = new GymClass(booking.getGymClass().getTitle(), booking.getGymClass().getDescription(),
                booking.getGymClass().getScheduledAt(), booking.getGymClass().getScheduleEnd(),
                booking.getGymClass().getCapacity(), booking.getGymClass().getTrainer());
        booking.setGymClass(gymClassRepository.save(gymClass));
    }

        public Optional<Booking> getBooking (Long id){
            return bookingRepository.findById(id);
        }

        public List<Booking> getAllBookings () {
            return bookingRepository.findAll();
        }

        public Booking updateBooking (Long id, Booking bookingDetails){
            checkIfBookingExists(id);
            bookingDetails.setId(id);
            return bookingRepository.save(bookingDetails);
        }

        public void deleteBooking (Long id){
            checkIfBookingExists(id);
            bookingRepository.deleteById(id);
        }

        private void checkIfBookingExists (Long id){
            if (!bookingRepository.existsById(id)) {
                log.warn("Cannot proceed - Booking ID {} not found", id);
                throw new IllegalArgumentException("Booking not found");
            }
        }
    }
