package rw.ac.auca.crypto_portfolio.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskAssessmentDTO {
    private Double riskScore;
    private String riskLevel;
    private String message;
    private String recommendation;
}
