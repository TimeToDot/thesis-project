package thesis.security.services.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SimplePositionAuth(
        UUID id,
        String name
) {
}
