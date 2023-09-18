package pl.thesis.domain.project.model.task;

import lombok.Builder;

@Builder
public record ProjectTaskDTO(
        Long id,
        Long projectId,
        String name
) {
}
