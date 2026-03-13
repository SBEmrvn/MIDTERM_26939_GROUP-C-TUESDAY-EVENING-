package rw.ac.auca.crypto_portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import rw.ac.auca.crypto_portfolio.entity.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    // Check if asset with same symbol exists
    boolean existsBySymbol(String symbol);

    // Check if asset with same name exists
    boolean existsByName(String name);

    // Find all assets with pagination and sorting
    Page<Asset> findAll(Pageable pageable);

    // Find assets by risk percentage less than or equal to
    Page<Asset> findByRiskPercentageLessThanEqual(
            Double riskPercentage, Pageable pageable);
}