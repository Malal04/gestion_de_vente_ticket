package tiket.isep.users.models.dtos;

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
public class UserD {
    private Integer id;
    private String nomComplete;
    private String telephone;
    private String email;
    private Role role;
}
