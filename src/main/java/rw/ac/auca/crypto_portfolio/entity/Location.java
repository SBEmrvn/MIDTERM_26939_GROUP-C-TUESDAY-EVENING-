package rw.ac.auca.crypto_portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Location parent;

    @OneToMany(mappedBy = "parent")
    private List<Location> children = new ArrayList<>();

    public Location getParent() {
        return this.parent;
    }
}
