package thesis.api.project.model.task;

import java.util.Date;
import java.util.UUID;

public record ProjectTaskResponse(
        UUID id,
        UUID projectId,
        String name,
        String description,
        Date creationDate,
        Date archiveDate,
        Boolean active
) {

}
