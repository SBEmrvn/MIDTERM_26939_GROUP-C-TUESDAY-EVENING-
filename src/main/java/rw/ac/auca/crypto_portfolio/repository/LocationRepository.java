package rw.ac.auca.crypto_portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.auca.crypto_portfolio.entity.Location;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    List<Location> findByType(String type);

    Optional<Location> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByNameAndType(String name, String type);

    boolean existsByNameAndTypeAndParentId(String name, String type, UUID parentId);

    List<Location> findByParentId(UUID parentId);

    @Query("SELECT l FROM Location l WHERE l.type = 'PROVINCE'")
    List<Location> findAllProvinces();

    @Query("SELECT l FROM Location l WHERE l.type = 'VILLAGE'")
    List<Location> findAllVillages();
}
