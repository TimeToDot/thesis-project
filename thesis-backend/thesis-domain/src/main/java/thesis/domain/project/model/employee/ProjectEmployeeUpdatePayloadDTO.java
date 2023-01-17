package thesis.domain.project.model.employee;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectEmployeeUpdatePayloadDTO(
        UUID projectEmployeeId,
        Integer modifier,
        Integer workingTime,
        RoleDTOStatus roleDTOStatus,
        Boolean active
) {
}
