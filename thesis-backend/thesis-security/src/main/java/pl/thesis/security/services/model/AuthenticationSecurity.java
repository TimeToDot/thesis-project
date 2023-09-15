package pl.thesis.security.services.model;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record AuthenticationSecurity(
        Long id,
        String username,
        String email,
        List<String> globalAuthorities,
        Map<Long, List<String>> projectPrivileges
) {
}
