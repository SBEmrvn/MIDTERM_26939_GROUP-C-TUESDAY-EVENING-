package rw.ac.auca.crypto_portfolio.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioHoldingDTO {
    private String assetName;
    private String symbol;
    private Double quantity;
    private Double averageBuyPrice;
    private Double currentPrice;
    private Double currentValue;
    private Double profitLoss;
    private Double profitLossPercentage;
}
