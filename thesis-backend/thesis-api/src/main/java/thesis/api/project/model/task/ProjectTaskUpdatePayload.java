package thesis.api.project.model.task;

import java.util.UUID;

public record ProjectTaskUpdatePayload(
        UUID id,
        String name,
        String description,
        Boolean active
) {

}
