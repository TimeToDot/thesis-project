package thesis.domain.project.model;

import java.util.List;

public record ProjectsResponse(
        List<ProjectResponse> projectResponses
) {
}
