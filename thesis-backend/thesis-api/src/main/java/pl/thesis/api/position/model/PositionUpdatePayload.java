package pl.thesis.api.position.model;

import java.util.UUID;

public record PositionUpdatePayload(
        String id,
        String name,
        String description,
        Boolean active
) {
}
