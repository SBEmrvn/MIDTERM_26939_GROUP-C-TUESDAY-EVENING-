# Code Style & Implementation Notes

## Java Version Compatibility

This project uses **Java 21** but follows **traditional Java coding patterns** suitable for academic projects.

---

## Coding Patterns Used

### ✅ Traditional For Loops
Instead of Java 8 streams, we use classic for loops:

```java
// Traditional approach
for (int i = 0; i < holdings.size(); i++) {
    PortfolioHolding holding = holdings.get(i);
    // process holding
}
```

### ✅ Manual Calculations
All calculations are done explicitly:

```java
// Calculate total value manually
double totalValue = 0;
for (int i = 0; i < holdings.size(); i++) {
    PortfolioHolding h = holdings.get(i);
    totalValue = totalValue + (h.getQuantity() * h.getAverageBuyPrice());
}
```

### ✅ Traditional Map Operations
Using basic Map methods:

```java
// Check and update map
if (byProvince.containsKey(provinceName)) {
    byProvince.put(provinceName, byProvince.get(provinceName) + 1);
} else {
    byProvince.put(provinceName, 1L);
}
```

### ✅ Explicit Conditionals
Clear if-else statements:

```java
if (invested > 0) {
    dto.setProfitLossPercentage((profitLoss / invested) * 100);
} else {
    dto.setProfitLossPercentage(0.0);
}
```

---

## Features That ARE Used (Standard Java)

### Optional (Java 8 - but basic usage)
```java
Optional<Portfolio> portfolio = portfolioRepository.findById(id);
if (portfolio.isPresent()) {
    // use portfolio.get()
}
```

### Enhanced For Loop (Java 5)
```java
for (String key : provinceKeys) {
    // process key
}
```

### Generics (Java 5)
```java
List<PortfolioHolding> holdings = new ArrayList<PortfolioHolding>();
Map<String, Double> percentages = new HashMap<String, Double>();
```

### Annotations (Standard Spring)
```java
@Service
@Autowired
@GetMapping
```

---

## Features NOT Used (Advanced Java 8+)

❌ **Streams API**
```java
// NOT USED
holdings.stream().filter(...).map(...).collect(...)
```

❌ **Lambda Expressions** (except in Optional.orElseThrow)
```java
// NOT USED
list.forEach(item -> process(item))
```

❌ **Method References**
```java
// NOT USED
.map(User::getName)
```

❌ **Functional Interfaces**
```java
// NOT USED
Function<T, R>, Predicate<T>, Consumer<T>
```

---

## Why This Approach?

1. **Academic Appropriateness** - Matches typical university curriculum
2. **Clarity** - Easy to understand and debug
3. **Explicit Logic** - Shows understanding of algorithms
4. **Traditional OOP** - Focuses on object-oriented principles

---

## Key Algorithms Implemented

### 1. Weighted Average Calculation
```java
double totalCost = (holding.getQuantity() * holding.getAverageBuyPrice()) + 
                   (quantity * price);
double newQuantity = holding.getQuantity() + quantity;
holding.setAverageBuyPrice(totalCost / newQuantity);
```

### 2. Portfolio Risk Scoring
```java
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
```

### 3. Percentage Distribution
```java
for (int i = 0; i < holdings.size(); i++) {
    PortfolioHolding holding = holdings.get(i);
    double value = holding.getQuantity() * holding.getAverageBuyPrice();
    double percentage = 0;
    if (totalValue > 0) {
        percentage = (value / totalValue) * 100;
    }
    percentages.put(holding.getAsset().getSymbol(), percentage);
}
```

### 4. Location Hierarchy Navigation
```java
private Location getProvince(Location location) {
    while (location != null && !location.getType().equals("PROVINCE")) {
        location = location.getParent();
    }
    return location;
}
```

---

## Spring Boot Features Used

### JPA/Hibernate
- Entity relationships (@OneToOne, @OneToMany, @ManyToOne, @ManyToMany)
- Repository pattern
- Custom queries with @Query
- Transaction management with @Transactional

### REST Controllers
- @RestController
- @RequestMapping
- @GetMapping, @PostMapping
- @PathVariable, @RequestParam
- ResponseEntity

### Dependency Injection
- @Autowired
- @Service
- @Repository

### Lombok
- @Getter, @Setter
- @NoArgsConstructor, @AllArgsConstructor
- Reduces boilerplate code

---

## Business Logic Highlights

### Automatic Portfolio Updates
When a transaction occurs, the system automatically:
1. Validates the transaction
2. Updates portfolio holdings
3. Recalculates average prices
4. Maintains data integrity

### Financial Calculations
- Profit/Loss tracking
- Weighted average buy price
- Portfolio diversification percentages
- Risk assessment scoring

### Data Validation
- Prevents overselling
- Validates sufficient balance
- Checks for duplicate portfolios
- Ensures data consistency

---

## Code Quality Practices

✅ **Separation of Concerns** - Controllers, Services, Repositories
✅ **DTO Pattern** - Clean API responses
✅ **Error Handling** - Meaningful exception messages
✅ **Null Safety** - Checks before operations
✅ **Transaction Management** - @Transactional for data integrity
✅ **Documentation** - Clear comments and API docs

---

## Summary

This project demonstrates:
- **Strong OOP principles**
- **Traditional algorithmic thinking**
- **Database design and relationships**
- **RESTful API development**
- **Business logic implementation**
- **Financial calculations**

All implemented using **standard, academic-appropriate Java patterns** that show genuine understanding of programming fundamentals.
