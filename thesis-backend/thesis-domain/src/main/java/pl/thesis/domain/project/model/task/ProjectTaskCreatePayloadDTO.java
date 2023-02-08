package pl.thesis.domain.project.model.task;

import java.util.UUID;

public record ProjectTaskCreatePayloadDTO(

        UUID projectId,
        String name,
        String description
) {

}
