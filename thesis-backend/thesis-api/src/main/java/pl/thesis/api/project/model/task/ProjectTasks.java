package pl.thesis.api.project.model.task;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectTasks(
        List<ProjectTaskDetails> tasks
) {
}
