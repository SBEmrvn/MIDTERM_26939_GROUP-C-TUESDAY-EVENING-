package rw.ac.auca.crypto_portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.crypto_portfolio.entity.Location;
import rw.ac.auca.crypto_portfolio.repository.LocationRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location save(String name, String code, String type, UUID parentId) {

        type = type.toUpperCase();

        if (locationRepository.existsByCode(code)) {
            throw new RuntimeException("Code " + code + " already exists");
        }

        if (type.equals("PROVINCE")) {
            if (locationRepository.existsByNameAndType(name, "PROVINCE")) {
                throw new RuntimeException("Province " + name + " already exists");
            }
            Location location = new Location();
            location.setName(name);
            location.setCode(code);
            location.setType("PROVINCE");
            location.setParent(null);
            return locationRepository.save(location);
        }

        Location parent = locationRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException(
                    "Parent not found with id: " + parentId));

        if (locationRepository.existsByNameAndTypeAndParentId(name, type, parentId)) {
            throw new RuntimeException(type + " " + name + " already exists under this parent");
        }

        Location location = new Location();
        location.setName(name);
        location.setCode(code);
        location.setType(type);
        location.setParent(parent);
        return locationRepository.save(location);
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public List<Location> findByType(String type) {
        return locationRepository.findByType(type.toUpperCase());
    }

    public Optional<Location> findById(UUID id) {
        return locationRepository.findById(id);
    }

    public List<Location> findAllProvinces() {
        return locationRepository.findAllProvinces();
    }

    public List<Location> findChildren(UUID parentId) {
        return locationRepository.findByParentId(parentId);
    }

    public Location getProvince(UUID locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        while (location.getParent() != null
               && !location.getType().equals("PROVINCE")) {
            location = location.getParent();
        }
        return location;
    }
}
