package thesis.domain.employee.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SimplePositionDTO(
        UUID id,
        String name
) {
}
