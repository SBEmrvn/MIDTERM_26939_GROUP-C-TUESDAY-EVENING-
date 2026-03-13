package rw.ac.auca.crypto_portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.crypto_portfolio.entity.Location;
import rw.ac.auca.crypto_portfolio.entity.User;
import rw.ac.auca.crypto_portfolio.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Save a new user
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByNationalId(user.getNationalId())) {
            throw new RuntimeException("National ID already registered");
        }
        return userRepository.save(user);
    }

    // Get all users
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Get user by id
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Get user by national ID
    public Optional<User> findByNationalId(String nationalId) {
        return userRepository.findByNationalId(nationalId);
    }

    // Get province of user using national ID and getParent() chain
    public Location getProvinceByNationalId(String nationalId) {
        User user = userRepository.findByNationalId(nationalId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getLocation()
                .getParent()
                .getParent()
                .getParent()
                .getParent();
    }

    // Get all users from a province by province name
    public List<User> findAllByProvinceName(String provinceName) {
        return userRepository.findAllByProvinceName(provinceName);
    }

    // Get all users from a province by province code
    public List<User> findAllByProvinceCode(String provinceCode) {
        return userRepository.findAllByProvinceCode(provinceCode);
    }
}