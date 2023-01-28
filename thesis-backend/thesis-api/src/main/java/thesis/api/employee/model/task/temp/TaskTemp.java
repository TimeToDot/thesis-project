package thesis.api.employee.model.task.temp;

import java.util.Date;
import java.util.UUID;

public record TaskTemp(
        UUID id,
        String name,
        String description,
        Date creationDate,
        Date archiveDate,
        Boolean active,
        UUID projectId
) {
}
