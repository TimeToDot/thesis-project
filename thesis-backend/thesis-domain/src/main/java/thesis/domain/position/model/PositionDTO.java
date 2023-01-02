package thesis.domain.position.model;

import java.util.UUID;

public record PositionDTO(
        UUID id,
        String name,
        String description,
        Integer employeesCount,
        Boolean active
) {
}
