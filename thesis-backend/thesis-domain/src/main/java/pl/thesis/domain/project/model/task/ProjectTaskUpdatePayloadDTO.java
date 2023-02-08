package pl.thesis.domain.project.model.task;

import java.util.UUID;

public record ProjectTaskUpdatePayloadDTO(
        UUID id,

        UUID projectId,

        String name,
        String description,
        Boolean active
) {

}
