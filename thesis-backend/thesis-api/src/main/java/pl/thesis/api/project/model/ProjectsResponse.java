package pl.thesis.api.project.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectsResponse(
        List<ProjectResponse> projects
) {
}
