package rw.ac.auca.crypto_portfolio.dto;

import lombok.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationAnalyticsDTO {
    private Map<String, Long> investorsByProvince;
    private Map<String, Long> investorsByDistrict;
    private String topProvince;
    private Long topProvinceCount;
}
