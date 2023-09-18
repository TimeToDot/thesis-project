package pl.thesis.domain.employee.model;

import lombok.Builder;

@Builder
public record SimplePositionDTO(
        Long id,
        String name
) {
}
