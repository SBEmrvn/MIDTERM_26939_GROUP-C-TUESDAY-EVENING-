package rw.ac.auca.crypto_portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import rw.ac.auca.crypto_portfolio.enums.TransactionType;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many transactions belong to ONE portfolio
    // One portfolio has MANY transactions
    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    // Many transactions can involve ONE asset
    // One asset can appear in MANY transactions
    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    // How many coins were bought or sold
    @Column(nullable = false)
    private Double quantity;

    // Price of the asset at the time of transaction
    @Column(nullable = false)
    private Double priceAtTransaction;

    // Total value = quantity x priceAtTransaction
    @Column(nullable = false)
    private Double totalValue;

    // BUY or SELL
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    // Automatically set when transaction is created
    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @PrePersist
    public void prePersist() {
        this.transactionDate = LocalDateTime.now();
        this.totalValue = this.quantity * this.priceAtTransaction;
    }
}