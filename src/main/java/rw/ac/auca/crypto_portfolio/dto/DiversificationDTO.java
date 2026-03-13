package rw.ac.auca.crypto_portfolio.dto;

import lombok.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiversificationDTO {
    private Map<String, Double> assetPercentages;
    private String dominantAsset;
    private Double dominantPercentage;
}
