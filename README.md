# Crypto Portfolio Management System

A comprehensive Spring Boot application for managing cryptocurrency investments with advanced analytics and location-based insights.

## Features

- 👤 **User Management** - Investor accounts with Rwanda location tracking
- 💰 **Portfolio Management** - Automated portfolio creation and tracking
- 📊 **Transaction Processing** - Buy/Sell with automatic holding updates
- 📈 **Smart Analytics** - Risk assessment, diversification, profit/loss tracking
- 🌍 **Location Intelligence** - Geographic investment trends across Rwanda
- 🏷️ **Asset Categorization** - Tag-based asset organization

## Tech Stack

- **Backend:** Spring Boot 3.x
- **Database:** PostgreSQL
- **ORM:** JPA/Hibernate
- **Build Tool:** Maven
- **Java Version:** 21

## Quick Start

1. **Clone the repository**
```bash
git clone <your-repo-url>
cd crypto-portfolio
```

2. **Configure database** (application.properties)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/crypto_portfolio_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

3. **Run the application**
```bash
mvn spring-boot:run
```

4. **Access the API**
```
http://localhost:8080/api
```

## Documentation

- 📖 [API Documentation](API_DOCUMENTATION.md) - Complete API reference
- 🚀 [Quick Start Guide](QUICK_START.md) - Step-by-step testing
- 📊 [Project Summary](PROJECT_SUMMARY.md) - Features and architecture

## Key Endpoints

### Analytics (Smart Features)
- `GET /api/analytics/portfolio/summary/{portfolioId}` - Dashboard overview
- `GET /api/analytics/portfolio/risk/{portfolioId}` - Risk assessment
- `GET /api/analytics/portfolio/diversification/{portfolioId}` - Asset distribution
- `GET /api/analytics/location/investors` - Geographic insights

### Core Operations
- `POST /api/transactions` - Buy/Sell crypto (auto-updates portfolio)
- `GET /api/portfolios/user/{userId}` - Get user portfolio
- `POST /api/assets` - Add cryptocurrency
- `POST /api/users` - Create investor account

## Sample Request

### Buy Bitcoin
```http
POST http://localhost:8080/api/transactions
Content-Type: application/json

{
  "portfolioId": 1,
  "assetId": 1,
  "quantity": 0.5,
  "priceAtTransaction": 60000,
  "type": "BUY"
}
```

### Get Portfolio Summary
```http
GET http://localhost:8080/api/analytics/portfolio/summary/1
```

Response:
```json
{
  "userName": "Jean Hakizimana",
  "portfolioValue": 48000.00,
  "totalProfitLoss": 3200.00,
  "totalAssets": 3,
  "bestAsset": "BTC",
  "worstAsset": "DOGE",
  "totalTransactions": 12
}
```

## Project Highlights

✅ **Automatic Portfolio Updates** - Transactions automatically update holdings
✅ **Weighted Average Calculation** - Tracks average buy price across purchases
✅ **Risk Assessment** - Portfolio risk scoring with recommendations
✅ **Location Analytics** - Unique Rwanda-specific investment insights
✅ **Data Integrity** - Prevents overselling, validates transactions
✅ **Professional API Design** - RESTful, paginated, comprehensive error handling

## Database Schema

- **users** - Investor accounts
- **locations** - Self-referencing hierarchy (Province → Village)
- **portfolios** - One per user
- **assets** - Cryptocurrency definitions
- **tags** - Asset categories
- **transactions** - Buy/Sell history
- **portfolio_holdings** - Current asset positions

## Business Logic

### Transaction Processing
1. Validate portfolio and asset
2. Record transaction
3. Update portfolio holdings:
   - **BUY:** Add to holdings, recalculate average price
   - **SELL:** Reduce holdings, validate sufficient balance
4. Maintain transaction history

### Risk Calculation
```
Portfolio Risk = Σ(Asset Weight × Asset Risk Percentage)
```

### Profit/Loss Tracking
```
P/L = (Current Price - Average Buy Price) × Quantity
```

## Author

Built with ❤️ for cryptocurrency investors in Rwanda

## License

MIT License
