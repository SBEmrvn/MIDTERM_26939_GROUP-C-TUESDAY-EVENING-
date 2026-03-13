package rw.ac.auca.crypto_portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.crypto_portfolio.entity.Location;
import rw.ac.auca.crypto_portfolio.entity.User;
import rw.ac.auca.crypto_portfolio.service.LocationService;
import rw.ac.auca.crypto_portfolio.service.UserService;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rw.ac.auca.crypto_portfolio.enums.Role;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    // POST http://localhost:8080/api/users
    @PostMapping
    public ResponseEntity<User> save(@RequestBody Map<String, Object> body) {
        User user = new User();
        user.setFirstName(body.get("firstName").toString());
        user.setLastName(body.get("lastName").toString());
        user.setEmail(body.get("email").toString());
        user.setNationalId(body.get("nationalId").toString());
        user.setPhoneNumber(body.get("phoneNumber").toString());
        user.setRole(Role.valueOf(body.get("role").toString().toUpperCase()));

        String locationIdStr = body.containsKey("locationId") 
                ? body.get("locationId").toString() 
                : body.get("villageId").toString();
        
        Location location = locationService.findById(UUID.fromString(locationIdStr))
                .orElseThrow(() -> new RuntimeException("Location not found"));
        user.setLocation(location);

        return ResponseEntity.ok(userService.save(user));
    }

    // GET http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // GET http://localhost:8080/api/users/1
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET http://localhost:8080/api/users/national/1200780123456789
    @GetMapping("/national/{nationalId}")
    public ResponseEntity<User> findByNationalId(
            @PathVariable String nationalId) {
        return userService.findByNationalId(nationalId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET http://localhost:8080/api/users/national/1200780123456789/province
    @GetMapping("/national/{nationalId}/province")
    public ResponseEntity<Location> getProvinceByNationalId(
            @PathVariable String nationalId) {
        Location province = userService.getProvinceByNationalId(nationalId);
        return ResponseEntity.ok(province);
    }

    // GET http://localhost:8080/api/users/province/name/Kigali
    @GetMapping("/province/name/{provinceName}")
    public ResponseEntity<List<User>> findByProvinceName(
            @PathVariable String provinceName) {
        return ResponseEntity.ok(
                userService.findAllByProvinceName(provinceName));
    }

    // GET http://localhost:8080/api/users/province/code/KGL
    @GetMapping("/province/code/{provinceCode}")
    public ResponseEntity<List<User>> findByProvinceCode(
            @PathVariable String provinceCode) {
        return ResponseEntity.ok(
                userService.findAllByProvinceCode(provinceCode));
    }
}