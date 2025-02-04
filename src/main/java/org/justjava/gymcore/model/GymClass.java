package org.justjava.gymcore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "classes")
public class GymClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    private int capacity;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;

    public GymClass() {}

    public GymClass(String title, String description, LocalDateTime scheduledAt, int capacity, User trainer) {
        this.title = title;
        this.description = description;
        this.scheduledAt = scheduledAt;
        this.capacity = capacity;
        this.trainer = trainer;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymClass gymClass = (GymClass) o;
        return getCapacity() == gymClass.getCapacity() &&
                Objects.equals(getId(), gymClass.getId()) &&
                Objects.equals(getTitle(), gymClass.getTitle()) &&
                Objects.equals(getDescription(), gymClass.getDescription()) &&
                Objects.equals(getScheduledAt(), gymClass.getScheduledAt())
                && Objects.equals(getTrainer(), gymClass.getTrainer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getScheduledAt(), getCapacity(), getTrainer());
    }

    @Override
    public String toString() {
        return "GymClass{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", scheduledAt=" + scheduledAt +
                ", capacity=" + capacity +
                ", trainer=" + trainer +
                '}';
    }
}
