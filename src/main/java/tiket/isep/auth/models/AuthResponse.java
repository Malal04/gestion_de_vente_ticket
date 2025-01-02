package tiket.isep.auth.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tiket.isep.users.models.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

    private String nomComplete;
    private String telephone;
    private String email;
    private String accessToken;
    private String refreshToken;
    private String message;
    private Role role;

}