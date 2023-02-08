package pl.thesis.domain.project.model.employee;

import java.util.UUID;

public record ProjectEmployeeUpdatePayloadDTO(

        UUID projectId,
        UUID projectEmployeeId,
        Integer modifier,
        Integer workingTime,
        //RoleDTOStatus roleDTOStatus,
        Boolean active
) {
}
