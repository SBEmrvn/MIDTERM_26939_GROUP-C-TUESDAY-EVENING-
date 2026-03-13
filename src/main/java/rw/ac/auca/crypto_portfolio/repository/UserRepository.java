package rw.ac.auca.crypto_portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.auca.crypto_portfolio.entity.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by national ID
    Optional<User> findByNationalId(String nationalId);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Check if national ID already exists
    boolean existsByNationalId(String nationalId);

    // Get all users from a specific province by province name
    @Query("SELECT u FROM User u WHERE u.location.parent.parent.parent.parent.name = :provinceName")
    List<User> findAllByProvinceName(String provinceName);

    // Get all users from a specific province by province code
    @Query("SELECT u FROM User u WHERE u.location.parent.parent.parent.parent.name = :provinceCode")
    List<User> findAllByProvinceCode(String provinceCode);
}