package rw.ac.auca.crypto_portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.crypto_portfolio.entity.Tag;
import rw.ac.auca.crypto_portfolio.service.TagService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    // POST http://localhost:8080/api/tags
    // Body: { "name": "DeFi" }
    @PostMapping
    public ResponseEntity<Tag> save(@RequestBody Map<String, Object> body) {
        String name = body.get("name").toString();
        return ResponseEntity.ok(tagService.save(name));
    }

    // GET http://localhost:8080/api/tags
    @GetMapping
    public ResponseEntity<List<Tag>> findAll() {
        return ResponseEntity.ok(tagService.findAll());
    }

    // GET http://localhost:8080/api/tags/1
    @GetMapping("/{id}")
    public ResponseEntity<Tag> findById(@PathVariable Long id) {
        return tagService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}