package thesis.api.auth.model;

import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
public record AuthenticationResponse(
        UUID id,
        String username,
        String email,
        List<String> globalAuthorities,
        Map<UUID, List<String>> projectPrivileges
) {

}
