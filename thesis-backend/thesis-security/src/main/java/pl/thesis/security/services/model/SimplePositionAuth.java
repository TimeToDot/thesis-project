package pl.thesis.security.services.model;

import lombok.Builder;

@Builder
public record SimplePositionAuth(
        Long id,
        String name
) {
}
