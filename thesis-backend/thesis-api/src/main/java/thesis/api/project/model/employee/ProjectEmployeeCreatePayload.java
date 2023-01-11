package thesis.api.project.model.employee;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectEmployeeCreatePayload(
        UUID projectEmployeeId,
        String contractType,
        LocalDateTime workingTime,
        Long wage
) {
}
