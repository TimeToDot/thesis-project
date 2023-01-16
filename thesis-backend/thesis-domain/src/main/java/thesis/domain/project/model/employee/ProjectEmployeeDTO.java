package thesis.domain.project.model.employee;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record ProjectEmployeeDTO(
        UUID projectEmployeeId,
        EmployeeDTO employee,
        String contractType,
        LocalDateTime workingTime,
        Long wage,
        Date joinDate,
        Date exitDate,
        Boolean active
) {
}
