package pl.thesis.domain.task.model;

import java.util.Date;
import java.util.UUID;

public record TaskFormDto(
        UUID id,
        String name,
        String description,
        Date creationDate,
        UUID projectId
) {
}
