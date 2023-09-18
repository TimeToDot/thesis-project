package pl.thesis.domain.position.model;

public record PositionUpdatePayloadDTO(
        Long id,
        String name,
        String description,
        Boolean active
) {
}
