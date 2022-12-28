package thesis.api.employee.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record EmployeeProjectsToApprovePayload(
        Date startDate,
        Date endDate,
        List<UUID> projectIds
) {
}
