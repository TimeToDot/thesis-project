package pl.thesis.domain.project.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectsDTO(
        List<ProjectDTO> projects
) {
}
