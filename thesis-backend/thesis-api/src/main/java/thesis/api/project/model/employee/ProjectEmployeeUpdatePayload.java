package thesis.api.project.model.employee;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectEmployeeUpdatePayload(
        UUID projectEmployeeId,
        String contractType,
        LocalDateTime workingTime,
        Long wage
) {
}
