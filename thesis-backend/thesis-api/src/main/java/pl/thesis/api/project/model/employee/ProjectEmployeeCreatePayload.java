package pl.thesis.api.project.model.employee;

import java.util.UUID;

public record ProjectEmployeeCreatePayload(
        UUID employeeId,
        Integer modifier,
        Integer workingTime
) {
}
