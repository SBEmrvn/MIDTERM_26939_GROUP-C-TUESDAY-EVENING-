package rw.ac.auca.crypto_portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.crypto_portfolio.entity.PortfolioHolding;
import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioHoldingRepository extends JpaRepository<PortfolioHolding, Long> {
    
    List<PortfolioHolding> findByPortfolioId(Long portfolioId);
    
    Optional<PortfolioHolding> findByPortfolioIdAndAssetId(Long portfolioId, Long assetId);
    
    boolean existsByPortfolioIdAndAssetId(Long portfolioId, Long assetId);
}
