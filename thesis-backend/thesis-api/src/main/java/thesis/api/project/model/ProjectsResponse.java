package thesis.api.project.model;

import java.util.List;

public record ProjectsResponse(
        List<ProjectResponse> projectResponses
) {
}
