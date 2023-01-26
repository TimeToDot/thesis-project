package thesis.domain.project.model.employee;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectEmployeeCreatePayloadDTO(
        UUID employeeId,
        Integer modifier,
        Integer workingTime
        //RoleDTOStatus roleDTOStatus
) {
}
