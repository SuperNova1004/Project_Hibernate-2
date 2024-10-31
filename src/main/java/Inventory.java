import jakarta.persistence.*;
import jdk.internal.util.ReferencedKeySet;

import java.util.List;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @OneToMany(mappedBy = "inventory")
    private List<Rental> rentals;

    public ReferencedKeySet<Object> getRentals() {

    }
}

    // Getters and setters

