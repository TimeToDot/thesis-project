package thesis.domain.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public record EmployeeProjectDetailsDTO(
        UUID id,
        String name,
        String imagePath,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date joinDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date exitDate,
        UUID projectEmployeeId,
        Boolean active
) {
}
