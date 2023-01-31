package pl.thesis.api.project.model.task;

import java.util.UUID;

public record ProjectTaskUpdatePayload(
        String id,
        String name,
        String description,
        Boolean active
) {

}
