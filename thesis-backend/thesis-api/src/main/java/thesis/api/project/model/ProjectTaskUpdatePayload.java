package thesis.api.project.model;

import java.util.UUID;

public record ProjectTaskUpdatePayload(
        UUID id,
        String name,
        String description,
        Boolean active
) {

}
