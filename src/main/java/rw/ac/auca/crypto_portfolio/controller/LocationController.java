package rw.ac.auca.crypto_portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.crypto_portfolio.entity.Location;
import rw.ac.auca.crypto_portfolio.service.LocationService;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Map<String, Object> body) {
        String name     = body.get("name").toString();
        String code     = body.get("code").toString();
        String type     = body.get("type").toString();
        UUID parentId   = body.containsKey("parentId") && body.get("parentId") != null
                ? UUID.fromString(body.get("parentId").toString())
                : null;
        return ResponseEntity.ok(locationService.save(name, code, type, parentId));
    }

    @GetMapping
    public ResponseEntity<List<Location>> findAll() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(@PathVariable UUID id) {
        return locationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Location>> findByType(@PathVariable String type) {
        return ResponseEntity.ok(locationService.findByType(type));
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<List<Location>> findChildren(@PathVariable UUID id) {
        return ResponseEntity.ok(locationService.findChildren(id));
    }

    @GetMapping("/{id}/province")
    public ResponseEntity<Location> getProvince(@PathVariable UUID id) {
        return ResponseEntity.ok(locationService.getProvince(id));
    }

    @GetMapping("/provinces")
    public ResponseEntity<List<Location>> findAllProvinces() {
        return ResponseEntity.ok(locationService.findAllProvinces());
    }
}
