package thesis.api.project.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record ProjectEmployeeResponse(
        UUID projectId,
        UUID employeeId,
        String firstName,
        String lastName,
        String email,
        String imagePath,
        String position,
        Date employmentDate,
        Long workingTime,
        Boolean active,
        String contractType,
        Long wage,
        Date joinDate,
        Date exitDate
) {
}
