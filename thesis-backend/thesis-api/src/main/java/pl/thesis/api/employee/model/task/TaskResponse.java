package pl.thesis.api.employee.model.task;

import java.util.Date;
import java.util.UUID;

public record TaskResponse(
        String id,
        String name,
        String description,
        Date creationDate,
        String projectId
) {
}
