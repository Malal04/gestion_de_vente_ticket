package tiket.isep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String phone;

    private boolean hasReserved = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

}