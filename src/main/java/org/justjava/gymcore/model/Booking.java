package org.justjava.gymcore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bookings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "class_id"})
})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private GymClass gymClass;

    @Column(name = "booking_time")
    private LocalDateTime bookingTime;

    public Booking() {
        this.bookingTime = LocalDateTime.now();
    }

    public Booking(User user, GymClass gymClass) {
        this.user = user;
        this.gymClass = gymClass;
        this.bookingTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GymClass getGymClass() {
        return gymClass;
    }

    public void setGymClass(GymClass gymClass) {
        this.gymClass = gymClass;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(getId(), booking.getId()) &&
                Objects.equals(getUser(), booking.getUser()) &&
                Objects.equals(getGymClass(), booking.getGymClass()) &&
                Objects.equals(getBookingTime(), booking.getBookingTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getGymClass(), getBookingTime());
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user=" + user +
                ", gymClass=" + gymClass +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
