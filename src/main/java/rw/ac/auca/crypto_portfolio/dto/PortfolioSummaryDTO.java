package rw.ac.auca.crypto_portfolio.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioSummaryDTO {
    private String userName;
    private Double portfolioValue;
    private Double totalProfitLoss;
    private Integer totalAssets;
    private String bestAsset;
    private String worstAsset;
    private Integer totalTransactions;
}
