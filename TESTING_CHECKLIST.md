# Testing Checklist - Crypto Portfolio System

## ✅ Pre-Testing Setup

- [ ] PostgreSQL is running
- [ ] Database `crypto_portfolio_db` exists
- [ ] Application is running on port 8080
- [ ] Postman is installed
- [ ] You have 10 users already created

---

## 📋 Test Sequence

### Phase 1: Asset Setup (5 minutes)

- [ ] Create Bitcoin (BTC) - Risk: 45%
- [ ] Create Ethereum (ETH) - Risk: 50%
- [ ] Create Solana (SOL) - Risk: 65%
- [ ] Create Cardano (ADA) - Risk: 55%
- [ ] Create Dogecoin (DOGE) - Risk: 85%
- [ ] Verify: GET /api/assets returns 5 assets

**Expected Result:** 5 assets created with different risk levels

---

### Phase 2: Portfolio Creation (3 minutes)

- [ ] Get all users: GET /api/users
- [ ] Note down user IDs (should have 10 users)
- [ ] Create portfolio for User 1
- [ ] Create portfolio for User 2
- [ ] Create portfolio for User 3
- [ ] Try creating second portfolio for User 1 (should fail)

**Expected Result:** 
- 3 portfolios created
- Error message: "User already has a portfolio"

---

### Phase 3: Buy Transactions (5 minutes)

#### User 1 - Balanced Portfolio
- [ ] Buy 0.5 BTC @ $60,000 (Total: $30,000)
- [ ] Buy 3 ETH @ $3,500 (Total: $10,500)
- [ ] Buy 50 SOL @ $150 (Total: $7,500)

**Expected Holdings:**
- BTC: 0.5 @ $60,000
- ETH: 3 @ $3,500
- SOL: 50 @ $150
- Total Value: $48,000

#### User 2 - High Risk Portfolio
- [ ] Buy 0.25 BTC @ $58,000 (Total: $14,500)
- [ ] Buy 10,000 DOGE @ $0.15 (Total: $1,500)

**Expected Holdings:**
- BTC: 0.25 @ $58,000
- DOGE: 10,000 @ $0.15
- Total Value: $16,000

#### User 3 - Conservative Portfolio
- [ ] Buy 1 BTC @ $60,000
- [ ] Buy 5 ETH @ $3,500

---

### Phase 4: Analytics Testing (10 minutes)

#### Test Portfolio Summary
- [ ] GET /api/analytics/portfolio/summary/1
- [ ] Verify userName is correct
- [ ] Verify portfolioValue = $48,000
- [ ] Verify totalAssets = 3
- [ ] Verify totalTransactions = 3

#### Test Portfolio Holdings
- [ ] GET /api/analytics/portfolio/holdings/1
- [ ] Verify 3 holdings returned
- [ ] Check BTC quantity = 0.5
- [ ] Check ETH quantity = 3
- [ ] Check SOL quantity = 50
- [ ] Verify all prices match

#### Test Diversification
- [ ] GET /api/analytics/portfolio/diversification/1
- [ ] Verify BTC percentage ≈ 62.5%
- [ ] Verify ETH percentage ≈ 21.9%
- [ ] Verify SOL percentage ≈ 15.6%
- [ ] Verify dominantAsset = "BTC"

#### Test Risk Assessment - User 1
- [ ] GET /api/analytics/portfolio/risk/1
- [ ] Verify riskLevel = "MEDIUM"
- [ ] Verify riskScore between 45-55
- [ ] Check recommendation message

#### Test Risk Assessment - User 2
- [ ] GET /api/analytics/portfolio/risk/2
- [ ] Verify riskLevel = "HIGH" (because of DOGE)
- [ ] Verify riskScore > 60
- [ ] Check warning message about high risk

---

### Phase 5: Sell Transactions (5 minutes)

#### Test Successful Sell
- [ ] User 1 sells 0.2 BTC @ $65,000
- [ ] Verify transaction created
- [ ] GET holdings - verify BTC quantity = 0.3
- [ ] Verify profit recorded

#### Test Insufficient Balance Error
- [ ] Try to sell 5 BTC (User 1 only has 0.3)
- [ ] Verify error: "Insufficient balance"
- [ ] Verify holdings unchanged

#### Test Complete Sell
- [ ] User 1 sells remaining 0.3 BTC
- [ ] GET holdings - verify BTC removed from list
- [ ] Verify only ETH and SOL remain

---

### Phase 6: Location Analytics (3 minutes)

- [ ] GET /api/analytics/location/investors
- [ ] Verify investorsByProvince shows counts
- [ ] Verify investorsByDistrict shows counts
- [ ] Verify topProvince is identified
- [ ] Check topProvinceCount matches

**Expected Result:** 
```json
{
  "investorsByProvince": {
    "Kigali": 6,
    "Northern": 2,
    "Southern": 2
  },
  "topProvince": "Kigali",
  "topProvinceCount": 6
}
```

---

### Phase 7: Advanced Scenarios (5 minutes)

#### Test Average Price Calculation
- [ ] User 3 buys 0.5 BTC @ $50,000
- [ ] User 3 buys 0.5 BTC @ $70,000
- [ ] GET holdings - verify average = $60,000
- [ ] Verify quantity = 1.0

#### Test Multiple Buys and Sells
- [ ] User 1 buys 2 ETH @ $3,600
- [ ] Verify new average price calculated
- [ ] Verify quantity = 5 ETH
- [ ] Sell 1 ETH
- [ ] Verify quantity = 4 ETH
- [ ] Verify average price unchanged

---

### Phase 8: Transaction History (3 minutes)

- [ ] GET /api/transactions/portfolio/1
- [ ] Verify all transactions listed
- [ ] Check transactions sorted by date (newest first)
- [ ] Verify BUY and SELL types correct
- [ ] Check totalValue calculated correctly

---

### Phase 9: Edge Cases (5 minutes)

#### Test Duplicate Portfolio
- [ ] Try creating second portfolio for existing user
- [ ] Verify error message

#### Test Invalid Asset
- [ ] Try transaction with assetId = 999
- [ ] Verify error: "Asset not found"

#### Test Invalid Portfolio
- [ ] Try transaction with portfolioId = 999
- [ ] Verify error: "Portfolio not found"

#### Test Zero Quantity
- [ ] Try buying 0 BTC
- [ ] System should handle gracefully

#### Test Negative Quantity
- [ ] Try buying -1 BTC
- [ ] System should reject

---

### Phase 10: Comparison Testing (5 minutes)

#### Compare Risk Levels
- [ ] Get risk for User 1 (balanced)
- [ ] Get risk for User 2 (high risk with DOGE)
- [ ] Verify User 2 has higher risk score
- [ ] Verify different recommendations

#### Compare Diversification
- [ ] Get diversification for User 1 (3 assets)
- [ ] Get diversification for User 2 (2 assets)
- [ ] Verify User 1 more diversified

---

## 🎯 Success Criteria

### Must Pass (Critical)
- ✅ All assets created successfully
- ✅ Portfolios created for users
- ✅ Buy transactions update holdings correctly
- ✅ Sell transactions reduce holdings
- ✅ Insufficient balance error works
- ✅ Portfolio summary shows correct data
- ✅ Risk assessment calculates properly
- ✅ Location analytics shows distribution

### Should Pass (Important)
- ✅ Average price calculated correctly
- ✅ Diversification percentages accurate
- ✅ Transaction history complete
- ✅ Error messages clear and helpful
- ✅ Pagination works

### Nice to Have (Bonus)
- ✅ All edge cases handled
- ✅ Performance is fast
- ✅ API responses clean and consistent

---

## 📊 Expected Final State

After all tests:

### User 1 Portfolio
- ETH: 4 units
- SOL: 50 units
- Total transactions: 6+
- Risk: MEDIUM

### User 2 Portfolio
- BTC: 0.25 units
- DOGE: 10,000 units
- Total transactions: 2
- Risk: HIGH

### User 3 Portfolio
- BTC: 1 unit (avg $60,000)
- ETH: 5 units
- Total transactions: 3
- Risk: MEDIUM-LOW

---

## 🐛 Common Issues & Solutions

### Issue: "Portfolio not found"
**Solution:** Make sure you created the portfolio first

### Issue: "Insufficient balance"
**Solution:** Check current holdings before selling

### Issue: "User already has a portfolio"
**Solution:** Each user can only have one portfolio

### Issue: Average price seems wrong
**Solution:** Check the weighted average formula:
```
(Qty1 × Price1 + Qty2 × Price2) / (Qty1 + Qty2)
```

---

## 📝 Testing Notes

Record your observations:

**What worked well:**
- 

**What needs improvement:**
- 

**Bugs found:**
- 

**Performance observations:**
- 

---

## ✨ Demo Highlights

When presenting, emphasize:

1. **Automatic Updates** - Show how buy/sell instantly updates holdings
2. **Smart Calculations** - Demonstrate average price calculation
3. **Risk Intelligence** - Compare low vs high risk portfolios
4. **Location Insights** - Show geographic distribution
5. **Error Handling** - Try to oversell and show validation
6. **Real-time Analytics** - Show how summary updates after transactions

---

## 🎓 What This Demonstrates

✅ Complex business logic
✅ Financial calculations
✅ Data integrity
✅ Error handling
✅ RESTful API design
✅ Database relationships
✅ Analytics capabilities
✅ Real-world problem solving

**You've built a production-ready crypto portfolio management system!** 🚀
