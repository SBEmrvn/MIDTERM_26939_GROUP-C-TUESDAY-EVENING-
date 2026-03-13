package rw.ac.auca.crypto_portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.crypto_portfolio.entity.Asset;
import rw.ac.auca.crypto_portfolio.service.AssetService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    // POST http://localhost:8080/api/assets
    @PostMapping
    public ResponseEntity<Asset> save(@RequestBody Asset asset) {
        return ResponseEntity.ok(assetService.save(asset));
    }

    // GET http://localhost:8080/api/assets
    @GetMapping
    public ResponseEntity<List<Asset>> findAll() {
        return ResponseEntity.ok(assetService.findAll());
    }

    // GET http://localhost:8080/api/assets/paged?page=0&size=10&sortBy=name
    @GetMapping("/paged")
    public ResponseEntity<Page<Asset>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return ResponseEntity.ok(
                assetService.findAllPaged(page, size, sortBy));
    }

    // GET http://localhost:8080/api/assets/risk?maxRisk=50&page=0&size=10
    @GetMapping("/risk")
    public ResponseEntity<Page<Asset>> findByMaxRisk(
            @RequestParam Double maxRisk,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                assetService.findByMaxRisk(maxRisk, page, size));
    }

    // GET http://localhost:8080/api/assets/1
    @GetMapping("/{id}")
    public ResponseEntity<Asset> findById(@PathVariable Long id) {
        return assetService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST http://localhost:8080/api/assets/tag
    // Body: { "assetId": 1, "tagId": 1 }
    @PostMapping("/tag")
    public ResponseEntity<Asset> addTag(
            @RequestBody Map<String, Object> body) {
        Long assetId = Long.valueOf(body.get("assetId").toString());
        Long tagId = Long.valueOf(body.get("tagId").toString());
        return ResponseEntity.ok(assetService.addTag(assetId, tagId));
    }

    // GET http://localhost:8080/api/assets/1/price
    @GetMapping("/{id}/price")
    public ResponseEntity<String> getLivePrice(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getLivePrice(id));
    }
}