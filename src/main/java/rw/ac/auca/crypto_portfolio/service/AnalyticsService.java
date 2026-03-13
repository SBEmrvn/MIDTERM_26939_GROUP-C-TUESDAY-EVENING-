package rw.ac.auca.crypto_portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.crypto_portfolio.dto.*;
import rw.ac.auca.crypto_portfolio.entity.*;
import rw.ac.auca.crypto_portfolio.enums.TransactionType;
import rw.ac.auca.crypto_portfolio.repository.*;

import java.util.*;

@Service
public class AnalyticsService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioHoldingRepository holdingRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    public PortfolioSummaryDTO getPortfolioSummary(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        List<PortfolioHolding> holdings = holdingRepository.findByPortfolioId(portfolioId);
        List<Transaction> transactions = transactionRepository.findByPortfolioId(portfolioId);

        double portfolioValue = 0;
        String bestAsset = "N/A";
        String worstAsset = "N/A";
        double maxValue = Double.NEGATIVE_INFINITY;
        double minValue = Double.POSITIVE_INFINITY;

        for (int i = 0; i < holdings.size(); i++) {
            PortfolioHolding holding = holdings.get(i);
            double currentValue = holding.getQuantity() * holding.getAverageBuyPrice();
            portfolioValue = portfolioValue + currentValue;

            if (currentValue > maxValue) {
                maxValue = currentValue;
                bestAsset = holding.getAsset().getSymbol();
            }
            if (currentValue < minValue) {
                minValue = currentValue;
                worstAsset = holding.getAsset().getSymbol();
            }
        }

        PortfolioSummaryDTO summary = new PortfolioSummaryDTO();
        summary.setUserName(portfolio.getUser().getFirstName() + " " + portfolio.getUser().getLastName());
        summary.setPortfolioValue(portfolioValue);
        summary.setTotalProfitLoss(0.0);
        summary.setTotalAssets(holdings.size());
        summary.setBestAsset(bestAsset);
        summary.setWorstAsset(worstAsset);
        summary.setTotalTransactions(transactions.size());

        return summary;
    }

    public List<PortfolioHoldingDTO> getPortfolioHoldings(Long portfolioId) {
        List<PortfolioHolding> holdings = holdingRepository.findByPortfolioId(portfolioId);
        List<PortfolioHoldingDTO> holdingDTOs = new ArrayList<PortfolioHoldingDTO>();

        for (int i = 0; i < holdings.size(); i++) {
            PortfolioHolding holding = holdings.get(i);
            PortfolioHoldingDTO dto = new PortfolioHoldingDTO();
            dto.setAssetName(holding.getAsset().getName());
            dto.setSymbol(holding.getAsset().getSymbol());
            dto.setQuantity(holding.getQuantity());
            dto.setAverageBuyPrice(holding.getAverageBuyPrice());
            dto.setCurrentPrice(holding.getAverageBuyPrice());
            
            double currentValue = holding.getQuantity() * holding.getAverageBuyPrice();
            dto.setCurrentValue(currentValue);
            dto.setProfitLoss(0.0);
            dto.setProfitLossPercentage(0.0);

            holdingDTOs.add(dto);
        }

        return holdingDTOs;
    }

    public DiversificationDTO getPortfolioDiversification(Long portfolioId) {
        List<PortfolioHolding> holdings = holdingRepository.findByPortfolioId(portfolioId);
        
        double totalValue = 0;
        for (int i = 0; i < holdings.size(); i++) {
            PortfolioHolding h = holdings.get(i);
            totalValue = totalValue + (h.getQuantity() * h.getAverageBuyPrice());
        }

        Map<String, Double> percentages = new HashMap<String, Double>();
        String dominantAsset = "";
        double dominantPercentage = 0;

        for (int i = 0; i < holdings.size(); i++) {
            PortfolioHolding holding = holdings.get(i);
            double value = holding.getQuantity() * holding.getAverageBuyPrice();
            double percentage = 0;
            if (totalValue > 0) {
                percentage = (value / totalValue) * 100;
            }
            percentages.put(holding.getAsset().getSymbol(), percentage);

            if (percentage > dominantPercentage) {
                dominantPercentage = percentage;
                dominantAsset = holding.getAsset().getSymbol();
            }
        }

        DiversificationDTO dto = new DiversificationDTO();
        dto.setAssetPercentages(percentages);
        dto.setDominantAsset(dominantAsset);
        dto.setDominantPercentage(dominantPercentage);

        return dto;
    }

    public RiskAssessmentDTO assessPortfolioRisk(Long portfolioId) {
        List<PortfolioHolding> holdings = holdingRepository.findByPortfolioId(portfolioId);
        
        double totalValue = 0;
        for (int i = 0; i < holdings.size(); i++) {
            PortfolioHolding h = holdings.get(i);
            totalValue = totalValue + (h.getQuantity() * h.getAverageBuyPrice());
        }

        double weightedRisk = 0;
        for (int i = 0; i < holdings.size(); i++) {
            PortfolioHolding holding = holdings.get(i);
            double value = holding.getQuantity() * holding.getAverageBuyPrice();
            double weight = 0;
            if (totalValue > 0) {
                weight = value / totalValue;
            }
            weightedRisk = weightedRisk + (weight * holding.getAsset().getRiskPercentage());
        }

        String riskLevel;
        String message;
        String recommendation;

        if (weightedRisk < 30) {
            riskLevel = "LOW";
            message = "Your portfolio has low risk exposure";
            recommendation = "Consider adding some growth assets for higher returns";
        } else if (weightedRisk < 60) {
            riskLevel = "MEDIUM";
            message = "Your portfolio has balanced risk";
            recommendation = "Maintain diversification across asset types";
        } else {
            riskLevel = "HIGH";
            message = "Your portfolio has high risk exposure";
            recommendation = "Consider rebalancing with more stable assets";
        }

        RiskAssessmentDTO dto = new RiskAssessmentDTO();
        dto.setRiskScore(weightedRisk);
        dto.setRiskLevel(riskLevel);
        dto.setMessage(message);
        dto.setRecommendation(recommendation);

        return dto;
    }

    public LocationAnalyticsDTO getLocationAnalytics() {
        List<User> users = userRepository.findAll();
        
        Map<String, Long> byProvince = new HashMap<String, Long>();
        Map<String, Long> byDistrict = new HashMap<String, Long>();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            Location location = user.getLocation();
            Location province = getProvince(location);
            Location district = getDistrict(location);

            if (province != null) {
                String provinceName = province.getName();
                if (byProvince.containsKey(provinceName)) {
                    byProvince.put(provinceName, byProvince.get(provinceName) + 1);
                } else {
                    byProvince.put(provinceName, 1L);
                }
            }
            
            if (district != null) {
                String districtName = district.getName();
                if (byDistrict.containsKey(districtName)) {
                    byDistrict.put(districtName, byDistrict.get(districtName) + 1);
                } else {
                    byDistrict.put(districtName, 1L);
                }
            }
        }

        String topProvince = "";
        Long topCount = 0L;
        
        Set<String> provinceKeys = byProvince.keySet();
        for (String key : provinceKeys) {
            Long count = byProvince.get(key);
            if (count > topCount) {
                topCount = count;
                topProvince = key;
            }
        }

        LocationAnalyticsDTO dto = new LocationAnalyticsDTO();
        dto.setInvestorsByProvince(byProvince);
        dto.setInvestorsByDistrict(byDistrict);
        dto.setTopProvince(topProvince);
        dto.setTopProvinceCount(topCount);

        return dto;
    }

    private Location getProvince(Location location) {
        while (location != null && !location.getType().equals("PROVINCE")) {
            location = location.getParent();
        }
        return location;
    }

    private Location getDistrict(Location location) {
        while (location != null && !location.getType().equals("DISTRICT")) {
            location = location.getParent();
        }
        return location;
    }
}
