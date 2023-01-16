package thesis.domain.project.model.task;

import java.util.UUID;

public record ProjectTaskUpdatePayloadDTO(
        UUID id,
        String name,
        String description,
        Boolean active
) {

}
