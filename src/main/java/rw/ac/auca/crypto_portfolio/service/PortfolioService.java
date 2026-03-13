package rw.ac.auca.crypto_portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.crypto_portfolio.entity.Portfolio;
import rw.ac.auca.crypto_portfolio.entity.User;
import rw.ac.auca.crypto_portfolio.repository.PortfolioRepository;
import rw.ac.auca.crypto_portfolio.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    // Create portfolio for a user
    public Portfolio save(Long userId, String name, String description) {

        // Find the user first
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(
                        "User not found with id: " + userId));

        // One-to-One check — user cannot have two portfolios
        if (portfolioRepository.existsByUserId(userId)) {
            throw new RuntimeException(
                    "User already has a portfolio");
        }

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setName(name);
        portfolio.setDescription(description);

        return portfolioRepository.save(portfolio);
    }

    // Get all portfolios
    public List<Portfolio> findAll() {
        return portfolioRepository.findAll();
    }

    // Get portfolio by id
    public Optional<Portfolio> findById(Long id) {
        return portfolioRepository.findById(id);
    }

    // Get portfolio by user id
    public Optional<Portfolio> findByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }
}