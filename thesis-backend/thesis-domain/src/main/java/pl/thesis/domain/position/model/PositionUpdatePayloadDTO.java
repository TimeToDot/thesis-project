package pl.thesis.domain.position.model;

import java.util.UUID;

public record PositionUpdatePayloadDTO(
        UUID id,
        String name,
        String description,
        Boolean active
) {
}
