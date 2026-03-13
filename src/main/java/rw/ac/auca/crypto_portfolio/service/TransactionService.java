package rw.ac.auca.crypto_portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.auca.crypto_portfolio.entity.Asset;
import rw.ac.auca.crypto_portfolio.entity.Portfolio;
import rw.ac.auca.crypto_portfolio.entity.PortfolioHolding;
import rw.ac.auca.crypto_portfolio.entity.Transaction;
import rw.ac.auca.crypto_portfolio.enums.TransactionType;
import rw.ac.auca.crypto_portfolio.repository.AssetRepository;
import rw.ac.auca.crypto_portfolio.repository.PortfolioHoldingRepository;
import rw.ac.auca.crypto_portfolio.repository.PortfolioRepository;
import rw.ac.auca.crypto_portfolio.repository.TransactionRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private AssetRepository assetRepository;
    
    @Autowired
    private PortfolioHoldingRepository holdingRepository;

    @Transactional
    public Transaction save(Long portfolioId, Long assetId,
                            Double quantity, Double priceAtTransaction,
                            TransactionType type) {

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException(
                        "Portfolio not found with id: " + portfolioId));

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException(
                        "Asset not found with id: " + assetId));

        Transaction transaction = new Transaction();
        transaction.setPortfolio(portfolio);
        transaction.setAsset(asset);
        transaction.setQuantity(quantity);
        transaction.setPriceAtTransaction(priceAtTransaction);
        transaction.setType(type);

        Transaction savedTransaction = transactionRepository.save(transaction);
        
        updatePortfolioHolding(portfolio, asset, quantity, priceAtTransaction, type);
        
        return savedTransaction;
    }
    
    private void updatePortfolioHolding(Portfolio portfolio, Asset asset, 
                                       Double quantity, Double price, 
                                       TransactionType type) {
        Optional<PortfolioHolding> existingHolding = 
            holdingRepository.findByPortfolioIdAndAssetId(portfolio.getId(), asset.getId());
        
        if (type == TransactionType.BUY) {
            if (existingHolding.isPresent()) {
                PortfolioHolding holding = existingHolding.get();
                double totalCost = (holding.getQuantity() * holding.getAverageBuyPrice()) + 
                                  (quantity * price);
                double newQuantity = holding.getQuantity() + quantity;
                holding.setQuantity(newQuantity);
                holding.setAverageBuyPrice(totalCost / newQuantity);
                holdingRepository.save(holding);
            } else {
                PortfolioHolding newHolding = new PortfolioHolding();
                newHolding.setPortfolio(portfolio);
                newHolding.setAsset(asset);
                newHolding.setQuantity(quantity);
                newHolding.setAverageBuyPrice(price);
                holdingRepository.save(newHolding);
            }
        } else if (type == TransactionType.SELL) {
            if (existingHolding.isPresent()) {
                PortfolioHolding holding = existingHolding.get();
                if (holding.getQuantity() < quantity) {
                    throw new RuntimeException("Insufficient balance. Available: " + 
                        holding.getQuantity() + ", Requested: " + quantity);
                }
                double newQuantity = holding.getQuantity() - quantity;
                if (newQuantity == 0) {
                    holdingRepository.delete(holding);
                } else {
                    holding.setQuantity(newQuantity);
                    holdingRepository.save(holding);
                }
            } else {
                throw new RuntimeException("No holdings found for this asset");
            }
        }
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public Page<Transaction> findByPortfolioId(
            Long portfolioId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("transactionDate").descending());
        return transactionRepository
                .findByPortfolioIdOrderByTransactionDateDesc(
                        portfolioId, pageable);
    }

    public List<Transaction> findByAssetId(Long assetId) {
        return transactionRepository.findByAssetId(assetId);
    }

    public List<Transaction> findByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    public Page<Transaction> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("transactionDate").descending());
        return transactionRepository.findAll(pageable);
    }
}