package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.entity.Product;
import org.example.entity.User;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        val product = getProductById(1);
        log.info("Product: {}", product);

        val productsByUserId = getProductsByUserId(1);
        log.info("All products by user_id: {}", productsByUserId);

        val allUsers = getAllUsers();
        log.info("All users: {}", allUsers);

        val userKirill = userRepository.findUserByUsername("Кирилл").orElseThrow(EntityNotFoundException::new);
        log.info("User: {}", userKirill);

        val newUser = createUser("Раиса");
        log.info("New user: {}", newUser);

        deleteUser(newUser.getId());

        val maxId = userRepository.getMaxId();
        log.info("Max id is: {}", maxId);
    }

    public User createUser(String username) {
        return userRepository.save(new User(username));
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public List<Product> getProductsByUserId(Integer userId) {
        return productRepository.findByUserId(userId);
    }
}
