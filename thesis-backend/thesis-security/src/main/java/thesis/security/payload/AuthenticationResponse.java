package thesis.security.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import thesis.data.role.model.RoleType;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
public class AuthenticationResponse {

    private UUID id;
    private String username;
    private String email;
    private List<String> roles;
}
