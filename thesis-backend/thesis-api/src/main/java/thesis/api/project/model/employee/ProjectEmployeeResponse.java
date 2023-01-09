package thesis.api.project.model.employee;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record ProjectEmployeeResponse(
        UUID projectEmployeeId,
        ProjectEmployee employee,
        String contractType,
        LocalDateTime workingTime,
        Long wage,
        Date joinDate,
        Date exitDate,
        Boolean active
) {
}
