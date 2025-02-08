package org.justjava.gymcore.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
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

    public GymClass(String title, String description, LocalDateTime scheduledAt, int capacity, User trainer) {
        this.title = title;
        this.description = description;
        this.scheduledAt = scheduledAt;
        this.capacity = capacity;
        this.trainer = trainer;
    }
}
