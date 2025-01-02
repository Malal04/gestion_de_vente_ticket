package tiket.isep.entity;

import jakarta.persistence.*;
import lombok.*;
import tiket.isep.users.models.entities.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer price;

    @Column(length = 200000000)
    private String image;

    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ticket(String name, Integer price, String image, Integer stockQuantity) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.stockQuantity = stockQuantity;
    }

}