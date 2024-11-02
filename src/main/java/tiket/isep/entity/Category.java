package tiket.isep.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Category(String name) {
        this.name = name;
    }

}
