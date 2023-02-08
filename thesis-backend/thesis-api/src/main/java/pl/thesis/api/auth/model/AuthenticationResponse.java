package pl.thesis.api.auth.model;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record AuthenticationResponse(
        String id,
        String username,
        String email,
        List<String> globalAuthorities,
        Map<String, List<String>> projectPrivileges
) {

}
