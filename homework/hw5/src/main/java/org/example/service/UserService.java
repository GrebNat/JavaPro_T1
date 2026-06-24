package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        var allUsers = getAllUsers();
        log.info("All users: {}", allUsers);

        var userKirill = userRepository.findUserByUsername("Кирилл").orElseThrow(EntityNotFoundException::new);
        log.info("User: {}", userKirill);

        var newUser = createUser("Рифкат");
        log.info("New user: {}", newUser);

        deleteUser(newUser.getId());

        var maxId = userRepository.getMaxId();
        log.info("Max id is: {}", maxId);
    }

    public User createUser(String username) {
        return userRepository.save(new User(username));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
