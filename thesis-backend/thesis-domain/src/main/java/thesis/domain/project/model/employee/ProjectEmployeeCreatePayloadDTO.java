package thesis.domain.project.model.employee;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectEmployeeCreatePayloadDTO(
        UUID projectEmployeeId,
        String contractType,
        LocalDateTime workingTime,
        Long wage
) {
}
