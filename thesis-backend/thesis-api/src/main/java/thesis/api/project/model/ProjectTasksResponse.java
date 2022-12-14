package thesis.api.project.model;

import java.util.List;

public record ProjectTasksResponse (
        List<ProjectTask> projectTasks
){
}
