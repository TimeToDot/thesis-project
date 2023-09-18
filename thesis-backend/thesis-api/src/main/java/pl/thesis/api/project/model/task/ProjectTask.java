package pl.thesis.api.project.model.task;

import lombok.Builder;

@Builder
public record ProjectTask(
        Long id,
        Long projectId,
        String name
) {
}
