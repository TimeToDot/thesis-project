package pl.thesis.api.project.model.employee;

import java.util.UUID;

public record ProjectEmployeeUpdatePayload(
        String employeeId,
        Integer modifier,
        Integer workingTime,
        //RoleDTOStatus roleDTOStatus,
        Boolean active
) {
}
