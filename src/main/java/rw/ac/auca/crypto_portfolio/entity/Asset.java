package rw.ac.auca.crypto_portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String symbol;

    // Used to fetch live price from CoinGecko API
    @Column(nullable = false, unique = true)
    private String coinGeckoId;

    @Column
    private String description;

    // Risk percentage chosen by asset manager e.g. 35.5 means 35.5% risk
    @Column(nullable = false)
    private Double riskPercentage;

    // Many-to-Many with Tag
    // One asset can have many tags
    // One tag can belong to many assets
    @ManyToMany
    @JoinTable(
            name = "asset_tags",
            joinColumns = @JoinColumn(name = "asset_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}