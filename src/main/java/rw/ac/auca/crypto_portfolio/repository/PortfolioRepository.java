package rw.ac.auca.crypto_portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.crypto_portfolio.entity.Portfolio;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    // Find portfolio by user id
    Optional<Portfolio> findByUserId(Long userId);

    // Check if user already has a portfolio
    boolean existsByUserId(Long userId);
}