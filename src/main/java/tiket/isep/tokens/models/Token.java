package tiket.isep.tokens.models;

import jakarta.persistence.*;
import lombok.*;
import tiket.isep.tokens.models.enums.TypeToken;
import tiket.isep.users.models.entities.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TypeToken typeToken = TypeToken.BEARER;

    private boolean revoked ;

    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @Override
//    public String toString() {
//        return "Token{" +
//                "user=" + user +
//                '}';
//    }

}