package thesis.api.employee.model.task;

import java.util.Date;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        String name,
        String description,
        Date creationDate,
        UUID projectId
) {
}
