package pl.thesis.api.employee.model.task.temp;

import java.util.Date;

public record TaskTemp(
        String id,
        String name,
        String description,
        Date creationDate,
        Date archiveDate,
        Boolean active,
        String projectId
) {
}
