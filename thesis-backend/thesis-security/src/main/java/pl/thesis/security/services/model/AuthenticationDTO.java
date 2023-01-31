package pl.thesis.security.services.model;

import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
public record AuthenticationDTO(
        UUID id,
        String username,
        String email,
        List<String> globalAuthorities,
        Map<UUID, List<String>> projectPrivileges
) {
}
