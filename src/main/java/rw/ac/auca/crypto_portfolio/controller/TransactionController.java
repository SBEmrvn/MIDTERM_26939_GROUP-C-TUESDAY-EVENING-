package rw.ac.auca.crypto_portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.crypto_portfolio.entity.Transaction;
import rw.ac.auca.crypto_portfolio.enums.TransactionType;
import rw.ac.auca.crypto_portfolio.service.TransactionService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // POST http://localhost:8080/api/transactions
    @PostMapping
    public ResponseEntity<Transaction> save(
            @RequestBody Map<String, Object> body) {
        Long portfolioId = Long.valueOf(
                body.get("portfolioId").toString());
        Long assetId = Long.valueOf(
                body.get("assetId").toString());
        Double quantity = Double.valueOf(
                body.get("quantity").toString());
        Double priceAtTransaction = Double.valueOf(
                body.get("priceAtTransaction").toString());
        TransactionType type = TransactionType.valueOf(
                body.get("type").toString().toUpperCase());

        return ResponseEntity.ok(transactionService.save(
                portfolioId, assetId, quantity,
                priceAtTransaction, type));
    }

    // GET http://localhost:8080/api/transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> findAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    // GET http://localhost:8080/api/transactions/1
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(
            @PathVariable Long id) {
        return transactionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET http://localhost:8080/api/transactions/portfolio/1?page=0&size=5
    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<Page<Transaction>> findByPortfolio(
            @PathVariable Long portfolioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                transactionService.findByPortfolioId(
                        portfolioId, page, size));
    }

    // GET http://localhost:8080/api/transactions/asset/1
    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<Transaction>> findByAsset(
            @PathVariable Long assetId) {
        return ResponseEntity.ok(
                transactionService.findByAssetId(assetId));
    }

    // GET http://localhost:8080/api/transactions/type/BUY
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> findByType(
            @PathVariable String type) {
        return ResponseEntity.ok(transactionService.findByType(
                TransactionType.valueOf(type.toUpperCase())));
    }

    // GET http://localhost:8080/api/transactions/paged?page=0&size=10
    @GetMapping("/paged")
    public ResponseEntity<Page<Transaction>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                transactionService.findAllPaged(page, size));
    }
}