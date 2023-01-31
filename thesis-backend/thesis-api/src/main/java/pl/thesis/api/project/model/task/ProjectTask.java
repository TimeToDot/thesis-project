package pl.thesis.api.project.model.task;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProjectTask(
        UUID id,
        UUID projectId,
        String name
) {
}
