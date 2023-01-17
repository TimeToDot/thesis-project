package thesis.api.employee.model.project;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EmployeeProjectsToApprovePayload(
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        Date startDate,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        Date endDate,
        List<UUID> projectIds
) {
}
