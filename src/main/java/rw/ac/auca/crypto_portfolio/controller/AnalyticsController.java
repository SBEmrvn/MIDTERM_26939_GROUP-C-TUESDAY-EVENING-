package rw.ac.auca.crypto_portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.crypto_portfolio.dto.*;
import rw.ac.auca.crypto_portfolio.service.AnalyticsService;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/portfolio/summary/{portfolioId}")
    public ResponseEntity<PortfolioSummaryDTO> getPortfolioSummary(
            @PathVariable Long portfolioId) {
        return ResponseEntity.ok(analyticsService.getPortfolioSummary(portfolioId));
    }

    @GetMapping("/portfolio/holdings/{portfolioId}")
    public ResponseEntity<List<PortfolioHoldingDTO>> getPortfolioHoldings(
            @PathVariable Long portfolioId) {
        return ResponseEntity.ok(analyticsService.getPortfolioHoldings(portfolioId));
    }

    @GetMapping("/portfolio/diversification/{portfolioId}")
    public ResponseEntity<DiversificationDTO> getPortfolioDiversification(
            @PathVariable Long portfolioId) {
        return ResponseEntity.ok(analyticsService.getPortfolioDiversification(portfolioId));
    }

    @GetMapping("/portfolio/risk/{portfolioId}")
    public ResponseEntity<RiskAssessmentDTO> assessPortfolioRisk(
            @PathVariable Long portfolioId) {
        return ResponseEntity.ok(analyticsService.assessPortfolioRisk(portfolioId));
    }

    @GetMapping("/location/investors")
    public ResponseEntity<LocationAnalyticsDTO> getLocationAnalytics() {
        return ResponseEntity.ok(analyticsService.getLocationAnalytics());
    }
}
