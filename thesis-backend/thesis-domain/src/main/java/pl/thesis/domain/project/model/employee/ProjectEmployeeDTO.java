package pl.thesis.domain.project.model.employee;

import lombok.Builder;

import java.util.Date;

@Builder
public record ProjectEmployeeDTO(
        Long projectEmployeeId,
        EmployeeDTO employee,
        Integer workingTime,
        Date joinDate,
        Date exitDate,
        Boolean active
) {
}
