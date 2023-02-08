package pl.thesis.api.project.model.employee;

import lombok.Builder;

import java.util.Date;

@Builder
public record ProjectEmployeeResponse(
        String projectEmployeeId,
        EmployeeResponse employee,
        Integer workingTime,
        Date joinDate,
        Date exitDate,
        Boolean active
) {
}
