package pl.thesis.domain.employee.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EmployeeProjectDTO(
        UUID id,
        String name,

        String imagePath
) {
}
