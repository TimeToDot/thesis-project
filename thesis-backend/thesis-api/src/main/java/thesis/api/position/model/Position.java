package thesis.api.position.model;

import java.util.UUID;

public record Position(
        UUID id,
        String name,
        String description,
        Integer employeesCount,
        Boolean active
) {
}
