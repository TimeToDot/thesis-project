package pl.thesis.domain.task.model;

import java.util.Date;

public record TaskFormDto(
        Long id,
        String name,
        String description,
        Date creationDate,
        Long projectId
) {
}
