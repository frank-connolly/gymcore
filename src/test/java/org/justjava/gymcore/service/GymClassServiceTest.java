package org.justjava.gymcore.service;

import org.justjava.gymcore.model.GymClass;
import org.justjava.gymcore.model.User;
import org.justjava.gymcore.model.UserRole;
import org.justjava.gymcore.repository.GymClassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GymClassServiceTest {

    @Mock
    private GymClassRepository gymClassRepository;

    @InjectMocks
    private GymClassService gymClassService;

    @Test
    void createGymClass_savesGymClass() {
        var trainer = new User("Trainer", "trainer@example.com", UserRole.TRAINER, null);
        trainer.setId(10L);
        var gymClass = new GymClass("Yoga", "Morning Yoga", LocalDateTime.of(2025, 2, 5, 8, 0), LocalDateTime.of(2025, 2, 5, 9, 0), 20, trainer);
        var savedGymClass = new GymClass("Yoga", "Morning Yoga", LocalDateTime.of(2025, 2, 5, 8, 0), LocalDateTime.of(2025, 2, 5, 9, 0), 20, trainer);
        savedGymClass.setId(1L);
        when(gymClassRepository.save(gymClass)).thenReturn(savedGymClass);

        var result = gymClassService.createGymClass(gymClass);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Yoga", result.getTitle());

        verify(gymClassRepository).save(gymClass);
    }

    @Test
    void getGymClass_returnsEmpty_whenNotFound() {
        when(gymClassRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<GymClass> result = gymClassService.getGymClass(99L);
        assertTrue(result.isEmpty());
        verify(gymClassRepository).findById(99L);
    }
}
