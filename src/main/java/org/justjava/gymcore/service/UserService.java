package org.justjava.gymcore.service;

import lombok.RequiredArgsConstructor;
import org.justjava.gymcore.model.User;
import org.justjava.gymcore.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User userDetails) {
        userDetails.setId(id);
        return userRepository.save(userDetails);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
