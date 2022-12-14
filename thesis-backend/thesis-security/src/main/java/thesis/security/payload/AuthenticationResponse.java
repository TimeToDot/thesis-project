package thesis.security.payload;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record AuthenticationResponse(
        UUID id,
        String username,
        String email,
        List<String> roles
) {

}
