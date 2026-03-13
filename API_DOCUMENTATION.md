# Crypto Portfolio Management API Documentation

## Base URL
```
http://localhost:8080/api
```

---

## 1. USERS API

### Create User
```http
POST /api/users
Content-Type: application/json

{
  "firstName": "Jean",
  "lastName": "Hakizimana",
  "email": "jean@crypto.rw",
  "nationalId": "1200780123456789",
  "phoneNumber": "0781234567",
  "locationId": "uuid-of-village",
  "role": "INVESTOR"
}
```

### Get All Users
```http
GET /api/users
```

### Get User by ID
```http
GET /api/users/{id}
```

### Get User by National ID
```http
GET /api/users/national/{nationalId}
```

### Get User's Province
```http
GET /api/users/national/{nationalId}/province
```

---

## 2. LOCATIONS API

### Create Location
```http
POST /api/locations
Content-Type: application/json

# Province (no parentId)
{
  "name": "Kigali",
  "code": "KGL",
  "type": "PROVINCE"
}

# District (needs province UUID)
{
  "name": "Gasabo",
  "code": "GSB",
  "type": "DISTRICT",
  "parentId": "province-uuid"
}
```

### Get All Locations
```http
GET /api/locations
```

### Get Locations by Type
```http
GET /api/locations/type/PROVINCE
GET /api/locations/type/DISTRICT
GET /api/locations/type/VILLAGE
```

### Get All Provinces
```http
GET /api/locations/provinces
```

### Get Location Children
```http
GET /api/locations/{id}/children
```

---

## 3. ASSETS API

### Create Asset
```http
POST /api/assets
Content-Type: application/json

{
  "name": "Bitcoin",
  "symbol": "BTC",
  "coinGeckoId": "bitcoin",
  "description": "The first cryptocurrency",
  "riskPercentage": 45.0
}
```

### Get All Assets
```http
GET /api/assets
```

### Get Asset by ID
```http
GET /api/assets/{id}
```

### Get Asset by Symbol
```http
GET /api/assets/symbol/BTC
```

---

## 4. TAGS API

### Create Tag
```http
POST /api/tags
Content-Type: application/json

{
  "name": "DeFi"
}
```

### Get All Tags
```http
GET /api/tags
```

### Add Tag to Asset
```http
POST /api/assets/{assetId}/tags/{tagId}
```

---

## 5. PORTFOLIOS API

### Create Portfolio
```http
POST /api/portfolios
Content-Type: application/json

{
  "userId": 1,
  "name": "My Crypto Portfolio",
  "description": "Long-term investment portfolio"
}
```

### Get All Portfolios
```http
GET /api/portfolios
```

### Get Portfolio by User ID
```http
GET /api/portfolios/user/{userId}
```

---

## 6. TRANSACTIONS API (MOST IMPORTANT)

### Buy Crypto
```http
POST /api/transactions
Content-Type: application/json

{
  "portfolioId": 1,
  "assetId": 1,
  "quantity": 0.5,
  "priceAtTransaction": 60000,
  "type": "BUY"
}
```

### Sell Crypto
```http
POST /api/transactions
Content-Type: application/json

{
  "portfolioId": 1,
  "assetId": 1,
  "quantity": 0.2,
  "priceAtTransaction": 65000,
  "type": "SELL"
}
```

### Get Portfolio Transactions
```http
GET /api/transactions/portfolio/{portfolioId}?page=0&size=10
```

### Get All Transactions
```http
GET /api/transactions
```

---

## 7. ANALYTICS API (SMART FEATURES)

### Portfolio Summary (Dashboard)
```http
GET /api/analytics/portfolio/summary/{portfolioId}

Response:
{
  "userName": "Jean Hakizimana",
  "portfolioValue": 15420.50,
  "totalProfitLoss": 3200.00,
  "totalAssets": 4,
  "bestAsset": "BTC",
  "worstAsset": "DOGE",
  "totalTransactions": 12
}
```

### Portfolio Holdings
```http
GET /api/analytics/portfolio/holdings/{portfolioId}

Response:
[
  {
    "assetName": "Bitcoin",
    "symbol": "BTC",
    "quantity": 0.75,
    "averageBuyPrice": 58000,
    "currentPrice": 60000,
    "currentValue": 45000,
    "profitLoss": 1500,
    "profitLossPercentage": 3.45
  }
]
```

### Portfolio Diversification
```http
GET /api/analytics/portfolio/diversification/{portfolioId}

Response:
{
  "assetPercentages": {
    "BTC": 60.5,
    "ETH": 25.0,
    "SOL": 14.5
  },
  "dominantAsset": "BTC",
  "dominantPercentage": 60.5
}
```

### Portfolio Risk Assessment
```http
GET /api/analytics/portfolio/risk/{portfolioId}

Response:
{
  "riskScore": 52.3,
  "riskLevel": "MEDIUM",
  "message": "Your portfolio has balanced risk",
  "recommendation": "Maintain diversification across asset types"
}
```

### Location Analytics (UNIQUE FEATURE)
```http
GET /api/analytics/location/investors

Response:
{
  "investorsByProvince": {
    "Kigali": 120,
    "Northern": 30,
    "Southern": 15
  },
  "investorsByDistrict": {
    "Gasabo": 65,
    "Kicukiro": 30
  },
  "topProvince": "Kigali",
  "topProvinceCount": 120
}
```

---

## COMPLETE USER FLOW EXAMPLE

### Step 1: Create a Portfolio for User
```http
POST /api/portfolios
{
  "userId": 1,
  "name": "Jean's Portfolio",
  "description": "Long-term crypto investments"
}
```

### Step 2: Buy Bitcoin
```http
POST /api/transactions
{
  "portfolioId": 1,
  "assetId": 1,
  "quantity": 0.5,
  "priceAtTransaction": 60000,
  "type": "BUY"
}
```

### Step 3: Buy Ethereum
```http
POST /api/transactions
{
  "portfolioId": 1,
  "assetId": 2,
  "quantity": 3,
  "priceAtTransaction": 3500,
  "type": "BUY"
}
```

### Step 4: View Portfolio Summary
```http
GET /api/analytics/portfolio/summary/1
```

### Step 5: Check Risk Level
```http
GET /api/analytics/portfolio/risk/1
```

### Step 6: View Diversification
```http
GET /api/analytics/portfolio/diversification/1
```

---

## SAMPLE ASSETS TO CREATE

```json
// Bitcoin
{
  "name": "Bitcoin",
  "symbol": "BTC",
  "coinGeckoId": "bitcoin",
  "description": "Digital gold",
  "riskPercentage": 45.0
}

// Ethereum
{
  "name": "Ethereum",
  "symbol": "ETH",
  "coinGeckoId": "ethereum",
  "description": "Smart contract platform",
  "riskPercentage": 50.0
}

// Solana
{
  "name": "Solana",
  "symbol": "SOL",
  "coinGeckoId": "solana",
  "description": "High-speed blockchain",
  "riskPercentage": 65.0
}

// Dogecoin
{
  "name": "Dogecoin",
  "symbol": "DOGE",
  "coinGeckoId": "dogecoin",
  "description": "Meme coin",
  "riskPercentage": 85.0
}
```

---

## SAMPLE TAGS

```json
{ "name": "DeFi" }
{ "name": "Layer1" }
{ "name": "Meme" }
{ "name": "Stablecoin" }
{ "name": "AI" }
```

---

## ERROR RESPONSES

### Insufficient Balance
```json
{
  "message": "Insufficient balance. Available: 0.3, Requested: 0.5"
}
```

### Portfolio Already Exists
```json
{
  "message": "User already has a portfolio"
}
```

### Asset Not Found
```json
{
  "message": "Asset not found with id: 999"
}
```
