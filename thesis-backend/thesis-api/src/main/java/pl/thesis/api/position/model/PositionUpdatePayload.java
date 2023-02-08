package pl.thesis.api.position.model;

public record PositionUpdatePayload(
        String id,
        String name,
        String description,
        Boolean active
) {
}
