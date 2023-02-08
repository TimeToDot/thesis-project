package pl.thesis.domain.project.model.task;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectTasksDTO(
        List<ProjectTaskDetailsDTO> tasks
) {
}
