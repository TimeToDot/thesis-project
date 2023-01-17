package thesis.domain.project.model.employee;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Builder
public record ProjectEmployeeDTO(
        UUID projectEmployeeId,
        EmployeeDTO employee,
        Integer workingTime,
        Date joinDate,
        Date exitDate,
        Boolean active
) {
}
