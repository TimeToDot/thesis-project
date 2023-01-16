package thesis.domain.project.model.task;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProjectTaskDTO(
        UUID id,
        UUID projectId,
        String name
) {
}
