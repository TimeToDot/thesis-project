package pl.thesis.domain.employee.model;

import lombok.Builder;

@Builder
public record EmployeeProjectDTO(
        Long id,
        String name,

        String imagePath
) {
}
