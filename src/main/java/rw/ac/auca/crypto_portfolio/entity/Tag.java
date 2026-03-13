package rw.ac.auca.crypto_portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Opposite side of the Many-to-Many relationship
    @ManyToMany(mappedBy = "tags")
    private Set<Asset> assets = new HashSet<>();
}