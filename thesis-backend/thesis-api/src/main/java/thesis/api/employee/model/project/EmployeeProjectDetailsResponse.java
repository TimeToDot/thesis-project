package thesis.api.employee.model.project;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.UUID;

public record EmployeeProjectDetailsResponse(
        UUID id,
        String name,
        String imagePath,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date joinDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date exitDate
) {
}
