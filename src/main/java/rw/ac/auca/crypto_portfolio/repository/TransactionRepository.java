package rw.ac.auca.crypto_portfolio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.auca.crypto_portfolio.entity.Transaction;
import rw.ac.auca.crypto_portfolio.enums.TransactionType;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByPortfolioId(Long portfolioId, Pageable pageable);

    List<Transaction> findByPortfolioId(Long portfolioId);

    List<Transaction> findByAssetId(Long assetId);

    List<Transaction> findByType(TransactionType type);

    Page<Transaction> findByPortfolioIdOrderByTransactionDateDesc(
            Long portfolioId, Pageable pageable);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.portfolio.id = :portfolioId")
    Long countByPortfolioId(Long portfolioId);
    
    List<Transaction> findByPortfolioIdAndAssetId(Long portfolioId, Long assetId);
}