package rw.ac.auca.crypto_portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.crypto_portfolio.entity.Portfolio;
import rw.ac.auca.crypto_portfolio.service.PortfolioService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    // POST http://localhost:8080/api/portfolios
    // Body: { "userId": 1, "name": "My Crypto Portfolio", "description": "..." }
    @PostMapping
    public ResponseEntity<Portfolio> save(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        String name = body.get("name").toString();
        String description = body.get("description").toString();
        return ResponseEntity.ok(
                portfolioService.save(userId, name, description));
    }

    // GET http://localhost:8080/api/portfolios
    @GetMapping
    public ResponseEntity<List<Portfolio>> findAll() {
        return ResponseEntity.ok(portfolioService.findAll());
    }

    // GET http://localhost:8080/api/portfolios/1
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> findById(@PathVariable Long id) {
        return portfolioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET http://localhost:8080/api/portfolios/user/1
    @GetMapping("/user/{userId}")
    public ResponseEntity<Portfolio> findByUserId(@PathVariable Long userId) {
        return portfolioService.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}