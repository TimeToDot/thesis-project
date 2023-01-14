package thesis.api.position.model;

import java.util.UUID;

public record PositionResponse(
        UUID id,
        String name,
        String description,
        Integer count,
        Boolean active
) {
}
