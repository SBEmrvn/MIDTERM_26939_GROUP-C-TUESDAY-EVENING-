package rw.ac.auca.crypto_portfolio.dto;

import lombok.*;
import rw.ac.auca.crypto_portfolio.enums.TransactionType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {
    private Long portfolioId;
    private Long assetId;
    private Double quantity;
    private Double priceAtTransaction;
    private TransactionType type;
}
